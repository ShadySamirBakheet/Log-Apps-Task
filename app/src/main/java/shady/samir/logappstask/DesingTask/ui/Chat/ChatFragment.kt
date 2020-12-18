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
import com.google.gson.Gson
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

    val gson: Gson = Gson()

    var roomName = "LogAppsTesk"


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
            Toast.makeText(context,"success"+socket.id(),Toast.LENGTH_LONG).show()
        } catch (e: URISyntaxException) {
            Toast.makeText(context, "Socket", Toast.LENGTH_LONG).show()
        }

        socket.on("new message", onNewMessage);
        socket!!.on(Socket.EVENT_CONNECT, onConnect)
        socket!!.on("user joined", onUserJoined)
        socket!!.on("user left", onUserLeft)
        socket.connect();

        btn_sendmassage.setOnClickListener {
            sendMessge(message.text.toString().trim())
        }

        return root
    }

    private fun sendMessge(messageText: String) {
        val content = messageText
        val sendData = SendMessage(UserName, content, roomName)
        val jsonData = gson.toJson(sendData)
        socket.emit("newMessage", jsonData)

        message.setText("")

        val message = MessageTexts(UserName, content, roomName, MessageType.CHAT_MINE.index)
        addMessage(message)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ChatViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private val onNewMessage = Emitter.Listener {
        val chat: MessageTexts = gson.fromJson(it[0].toString(), MessageTexts::class.java)
        chat.viewType = MessageType.CHAT_PARTNER.index
        addMessage(chat)
    }


    private val onConnect = Emitter.Listener {
        val data = InitialData(UserName, roomName)
        val jsonData = gson.toJson(data)
        socket.emit("subscribe", jsonData)
        Toast.makeText(context,"subscribe",Toast.LENGTH_LONG).show()
    }

    private val onUserJoined = Emitter.Listener {
        val name = it[0] as String //This pass the userName!
        val chat = MessageTexts(name, "", roomName, MessageType.USER_JOIN.index)
        addMessage(chat)
        Toast.makeText(context,"on New User triggered",Toast.LENGTH_LONG).show()
    }

    private val onUserLeft = Emitter.Listener {
        val leftUserName = it[0] as String
        val chat = MessageTexts(leftUserName, "", "", MessageType.USER_LEAVE.index)
        addMessage(chat)
    }

    private fun addMessage(chat:MessageTexts) {
        list.add(chat)
        messageitem.adapter=MessageAdapter(context, list, UserName)
    }


    override fun onDestroy() {
        super.onDestroy()
        socket.disconnect()
        socket.off(Socket.EVENT_CONNECT, onConnect)
        socket.off("new message", onNewMessage)
        socket.off("user joined", onUserJoined)
        socket.off("user left", onUserLeft)
    }

}