package com.github.nikeapp_master.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.nikeapp_master.R
import com.github.nikeapp_master.model.Items
import kotlinx.android.synthetic.main.item_query.view.*

class DefinitionAdapter : RecyclerView.Adapter<DefinitionAdapter.ViewHolder>() {

    var queryList: List<Items.Info> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.item_query,
            parent,
            false
        )
        return ViewHolder(view)
    }

    fun setQueries(queryList: List<Items.Info>){
        this.queryList = queryList
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(queryList[position])
    }

    override fun getItemCount(): Int {
        return queryList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val author = itemView.author
        private val definition = itemView.definition
        private val example = itemView.example
        private val permalink = itemView.permalink
        private val thumbsDown = itemView.thumbsDown
        private val thumbsUp = itemView.thumbsUp
        private val writtenOn = itemView.writtenOn
        private val raiting = itemView.raiting
        private val display = itemView.displayImage

        private var myImageList = intArrayOf(
            R.drawable.icon_0001,
            R.drawable.icon_0002,
            R.drawable.icon_0003,
            R.drawable.icon_0004,
            R.drawable.icon_0005,
            R.drawable.icon_0006,
            R.drawable.icon_0007,
            R.drawable.icon_0008,
            R.drawable.icon_0009
        )

        fun onBind(items: Items.Info) {

            author.text = items.author
            definition.text = items.definition
            example.text = items.example
            permalink.text = items.permalink
            thumbsDown.text = items.thumbs_down.toString()
            thumbsUp.text = items.thumbs_up.toString()
            writtenOn.text = items.written_on
            raiting.progress = items.thumbs_up
            val randomImg = (0..8).random()
            display.setBackgroundResource(myImageList[randomImg])


        }


    }
}