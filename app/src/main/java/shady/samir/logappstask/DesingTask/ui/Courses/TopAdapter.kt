package shady.samir.logappstask.DesingTask.ui.Courses

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import shady.samir.logappstask.R

class TopAdapter (
    var context: Context?,
    var list: List<Int>
) :
    RecyclerView.Adapter<TopAdapter.ViewHolder>(){

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val kotlinView: ImageView
        init {
            kotlinView = view.findViewById(shady.samir.logappstask.R.id.kotlinView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.kotlin_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val id = list[position]

        holder.apply {
            kotlinView.setImageResource(id)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}