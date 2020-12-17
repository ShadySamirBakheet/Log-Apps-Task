package shady.samir.logappstask.DesingTask.ui.Chat

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONException
import org.json.JSONObject
import shady.samir.logappstask.R
import java.net.URISyntaxException


class ChatFragment : Fragment() {

    lateinit var messageitem:RecyclerView
    lateinit var btn_sendmassage:ImageButton
    lateinit var message:TextInputEditText
    lateinit var user_name:TextInputEditText
    lateinit var btn_Login:Button
    lateinit var enterName:RelativeLayout
    lateinit var chatLayout:RelativeLayout
    lateinit var userNum:TextView

    lateinit var list: ArrayList<MessageTexts>

    lateinit var UserName:String

    lateinit var socket: Socket

    private var mTyping = false
    private val mTypingHandler = Handler()

    private var isConnected = true


    companion object {
        fun newInstance() = ChatFragment()
    }

    private lateinit var viewModel: ChatViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root =  inflater.inflate(R.layout.chat_fragment, container, false)

        message = root.findViewById(R.id.message)
        messageitem = root.findViewById(R.id.messageitem)
        btn_sendmassage = root.findViewById(R.id.btn_sendmassage)
        user_name = root.findViewById(R.id.user_name)
        btn_Login = root.findViewById(R.id.btn_Login)
        enterName = root.findViewById(R.id.enterName)
        chatLayout = root.findViewById(R.id.chatLayout)
        userNum = root.findViewById(R.id.userNum)

        messageitem.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(context)
        messageitem.layoutManager = layoutManager

        chatLayout.visibility = View.GONE
        enterName.visibility = View.VISIBLE

        btn_Login.setOnClickListener {
            UserName = user_name.text.toString().trim()
            if (TextUtils.isEmpty(UserName)) {
                Toast.makeText(context, "Enter Your Name", Toast.LENGTH_LONG).show()
            }else{
                chatLayout.visibility = View.VISIBLE
                enterName.visibility = View.GONE
                socket.emit("add user", UserName);
            }

        }

        list = ArrayList()

        try {
            this.socket = IO.socket("http://chat.socket.io")
        } catch (e: URISyntaxException) {
            Toast.makeText(context, "Socket", Toast.LENGTH_LONG).show()
        }

        socket.on("new message", onNewMessage);
        socket.on("login", onLogin)
        socket!!.on(Socket.EVENT_CONNECT, onConnect)
        socket!!.on(Socket.EVENT_DISCONNECT, onDisconnect)
        socket!!.on(Socket.EVENT_CONNECT_ERROR, onConnectError)
        socket!!.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError)
        socket!!.on("user joined", onUserJoined)
        socket!!.on("user left", onUserLeft)
        socket!!.on("typing", onTyping)
        socket!!.on("stop typing", onStopTyping)
        socket.connect();

        btn_sendmassage.setOnClickListener {
            sendMessge(message.text.toString().trim())
        }

        return root
    }

    private fun sendMessge(messageText: String) {
        if (TextUtils.isEmpty(messageText)) {
            Toast.makeText(context, "Enter Your Message", Toast.LENGTH_LONG).show()
            return
        }
        message.setText("")
        addMessage(UserName, messageText)
        val sendText = JSONObject()
        try {
            sendText.put("text", messageText)
            socket.emit("new message", sendText)
        } catch (e: JSONException) {
            Toast.makeText(context, "Send", Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ChatViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private val onNewMessage = Emitter.Listener { args ->
        activity?.runOnUiThread(Runnable {
            val data = args[0] as JSONObject
            val username: String
            val message: String
            try {
                username = data.getString("username")
                message = data.getString("text")
            } catch (e: JSONException) {
                Toast.makeText(context, "onNewMessage", Toast.LENGTH_LONG).show()
                return@Runnable
            }

            // add the message to view
            addMessage(username, message)
        })
    }

    private val onLogin = Emitter.Listener { args ->
        val data = args[0] as JSONObject
        val numUsers: Int
        numUsers = try {
            data.getInt("numUsers")
        } catch (e: JSONException) {
            return@Listener
        }

        showNumRoom(numUsers)
    }


    @SuppressLint("UseRequireInsteadOfGet")
    private val onConnect = Emitter.Listener {
        activity!!.runOnUiThread {
            if (!isConnected) {
                if (null != UserName) socket.emit("add user", UserName)
                Toast.makeText(
                    activity!!.applicationContext,
                    "connect", Toast.LENGTH_LONG
                ).show()
                isConnected = true
            }
        }
    }

    @SuppressLint("UseRequireInsteadOfGet")
    private val onDisconnect = Emitter.Listener {
        activity!!.runOnUiThread {
            Log.i(
                "TAG",
                "diconnected"
            )
            isConnected = false
            Toast.makeText(
                activity!!.applicationContext,
                "disconnect", Toast.LENGTH_LONG
            ).show()
        }
    }

    @SuppressLint("UseRequireInsteadOfGet")
    private val onConnectError = Emitter.Listener {
        activity!!.runOnUiThread {
            Log.e(
                "TAG",
                "Error connecting"
            )
            Toast.makeText(activity!!.applicationContext,
                "error_connect", Toast.LENGTH_LONG
            ).show()
        }
    }


    @SuppressLint("UseRequireInsteadOfGet")
    private val onUserJoined = Emitter.Listener { args ->
        activity!!.runOnUiThread(Runnable {
            val data = args[0] as JSONObject
            val username: String
            val numUsers: Int
            try {
                username = data.getString("username")
                numUsers = data.getInt("numUsers")
            } catch (e: JSONException) {
                Log.e(
                    "TAG",
                    e.message!!
                )
                return@Runnable
            }
            //addLog(resources.getString(R.string.message_user_joined, username))
            showNumRoom(numUsers)
        })
    }

    private val onUserLeft = Emitter.Listener { args ->
        requireActivity().runOnUiThread(Runnable {
            val data = args[0] as JSONObject
            val username: String
            val numUsers: Int
            try {
                username = data.getString("username")
                numUsers = data.getInt("numUsers")
            } catch (e: JSONException) {
                Log.e(
                    "TAG",
                    e.message!!
                )
                return@Runnable
            }
            // addLog(resources.getString(R.string.message_user_left, username))
            showNumRoom(numUsers)
            // removeTyping(username)
        })
    }

    @SuppressLint("UseRequireInsteadOfGet")
    private val onTyping = Emitter.Listener { args ->
        activity!!.runOnUiThread(Runnable {
            val data = args[0] as JSONObject
            val username: String
            username = try {
                data.getString("username")
            } catch (e: JSONException) {
                Log.e(
                    "TAG",
                    e.message!!
                )
                return@Runnable
            }
            addTyping(username)
        })
    }

    private fun addTyping(username: String) {

    }

    @SuppressLint("UseRequireInsteadOfGet")
    private val onStopTyping = Emitter.Listener { args ->
        activity!!.runOnUiThread(Runnable {
            val data = args[0] as JSONObject
            val username: String
            username = try {
                data.getString("username")
            } catch (e: JSONException) {
                Log.e(
                    "TAG",
                    e.message!!
                )
                return@Runnable
            }
            //removeTyping(username)
        })
    }

    private val onTypingTimeout = Runnable {
        if (!mTyping) return@Runnable
        mTyping = false
        socket.emit("stop typing")
    }

    private fun showNumRoom(numUsers: Int) {
        userNum.text = numUsers.toString()+" User opened Now"
    }


    private fun addMessage(username: String, message: String) {
        list.add(MessageTexts(username, message))
        messageitem.adapter=MessageAdapter(context, list, UserName)
    }


    override fun onDestroy() {
        super.onDestroy()
        socket.disconnect()
        socket.off("new message", onNewMessage)
        socket.off("login", onLogin)
        socket.off(Socket.EVENT_CONNECT, onConnect)
        socket.off(Socket.EVENT_DISCONNECT, onDisconnect)
        socket.off(Socket.EVENT_CONNECT_ERROR, onConnectError)
        socket.off(Socket.EVENT_CONNECT_TIMEOUT, onConnectError)
        socket.off("new message", onNewMessage)
        socket.off("user joined", onUserJoined)
        socket.off("user left", onUserLeft)
        socket.off("typing", onTyping)
        socket.off("stop typing", onStopTyping)
    }

}