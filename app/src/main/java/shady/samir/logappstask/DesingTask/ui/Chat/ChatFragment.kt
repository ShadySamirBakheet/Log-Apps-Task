package shady.samir.logappstask.DesingTask.ui.Chat

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.Toast
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

    lateinit var list: ArrayList<MessageTexts>

    lateinit var UserName:String

    lateinit var socket: Socket

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
            }

        }

        list = ArrayList()

        try {
            this.socket = IO.socket("http://chat.socket.io")
        } catch (e: URISyntaxException) {
            Toast.makeText(context, "Socket", Toast.LENGTH_LONG).show()
        }

        socket.on("new message", onNewMessage);
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
        addMessage(UserName,messageText)
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

    private fun addMessage(username: String, message: String) {
        list.add(MessageTexts(username, message))
        messageitem.adapter=MessageAdapter(context, list, UserName)
    }


    override fun onDestroy() {
        super.onDestroy()
        socket.disconnect()
        socket.off("new message", onNewMessage)
    }

}