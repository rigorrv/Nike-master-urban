package com.github.nikeapp_master.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.nikeapp_master.R
import com.github.nikeapp_master.model.Items
import kotlinx.android.synthetic.main.item_query.view.*
import kotlinx.android.synthetic.main.query_fragment.view.*

class QueryFragment : Fragment() {

    lateinit var rootView : View
    val TAG : String = "TAG"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.query_fragment,container,false)
        return rootView
    }

    fun loadQuery(queryList : Items, sortList : Boolean){
        if (sortList) {
            var sort = queryList.list.sortedBy { it.thumbs_up  }
            rootView.queryRecyclerView.layoutManager =  LinearLayoutManager(activity)
            val queryDapater = Adapter()
            rootView.queryRecyclerView.adapter= queryDapater
            queryDapater.queryList = sort
        }else {
            var sort = queryList.list.sortedByDescending { it.thumbs_up  }
            rootView.queryRecyclerView.layoutManager =  LinearLayoutManager(activity)
            val queryDapater = Adapter()
            rootView.queryRecyclerView.adapter= queryDapater
            queryDapater.queryList = sort
        }


    }

    class Adapter : RecyclerView.Adapter<Adapter.ViewHolder>(){

        lateinit var queryList : List<Items.Info>
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter.ViewHolder {
            val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_query,parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: Adapter.ViewHolder, position: Int) {
            holder.onBind(queryList[position])
        }

        override fun getItemCount(): Int {
            return queryList.size
        }
        class ViewHolder(view : View): RecyclerView.ViewHolder(view){

            val TAG : String = "TAG"
            val author = itemView.author
            val definition = itemView.definition
            val example = itemView.example
            val permalink = itemView.permalink
            val sound_urls = itemView.sound_urls
            val thumbs_down = itemView.thumbs_down
            val thumbs_up = itemView.thumbs_up
            val written_on = itemView.written_on
            val raiting = itemView.raiting
            val display = itemView.displayImage
            val soundPlayer = itemView.soundPlayer


            var myImageList = intArrayOf(R.drawable.icon_0001, R.drawable.icon_0002,R.drawable.icon_0003,R.drawable.icon_0004,R.drawable.icon_0005,R.drawable.icon_0006,R.drawable.icon_0007,R.drawable.icon_0008,R.drawable.icon_0009)


            fun onBind(items : Items.Info){

                author.text = items.author
                //defid.text = items.defid.toString()
                definition.text = items.definition
                example.text = items.example
                permalink.text = items.permalink
                //music here
                //sound_urls.text = items.sound_urls.toString()
                thumbs_down.text = items.thumbs_down.toString()
                thumbs_up.text = items.thumbs_up.toString()
                written_on.text = items.written_on
                //rateStars.rating = 5f
                raiting.progress =   items.thumbs_up
                var randomImg = (0..8).random()
                display.setBackgroundResource(myImageList[randomImg])
                sound_urls.setOnClickListener {
                    Log.d(TAG, "onBind: play sonund")

                }
            }
        }

    }
}