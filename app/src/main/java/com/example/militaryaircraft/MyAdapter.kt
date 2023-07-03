package com.example.militaryaircraft

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(val context : Activity, val quoteList : List<Quote>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.eachitem,parent,false )
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return quoteList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = quoteList[position]
        holder.author.text = currentItem.author
        holder.content.text = currentItem.quote
    }


    class MyViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        var author : TextView
        var content : TextView

        init {
            author = itemView.findViewById(R.id.Authorname)
            content = itemView.findViewById(R.id.Content)

        }
    }
}