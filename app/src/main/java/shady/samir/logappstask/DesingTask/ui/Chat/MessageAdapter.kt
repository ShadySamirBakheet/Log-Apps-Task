package shady.samir.logappstask.DesingTask.ui.Chat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import shady.samir.logappstask.R

class MessageAdapter(
        var context: Context?,
        var list: List<MessageTexts>,
        var mUserName:String
) : RecyclerView.Adapter<MessageAdapter.ViewHolder>(){

    val MES_TYPE_LEFT = 0
    val MES_TYPE_RIGHT = 1

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var showmessage: TextView
        var username: TextView

        init {
            showmessage = itemView.findViewById(R.id.showmessage)
            username = itemView.findViewById(R.id.username)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == MES_TYPE_RIGHT) {
            val view = LayoutInflater.from(context).inflate(R.layout.chatitemright, parent, false)
            ViewHolder(view)
        } else {
            val view = LayoutInflater.from(context).inflate(R.layout.chatitemleft, parent, false)
            ViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message: MessageTexts = list.get(position)
        holder.showmessage.setText(message.messageContent)
        holder.username.setText(message.userName)
    }

    override fun getItemCount(): Int {
        return list.size
    }


    override fun getItemViewType(position: Int): Int {
        return if (list.get(position).userName.equals(mUserName)) {
            MES_TYPE_RIGHT
        } else {
            MES_TYPE_LEFT
        }
    }

}