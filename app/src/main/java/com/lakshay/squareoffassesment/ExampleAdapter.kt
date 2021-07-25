package com.lakshay.squareoffassesment

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ExampleAdapter(var context : Context) : RecyclerView.Adapter<ExampleViewHolder>() {
    private val items: ArrayList<dataList> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.port_item_layout,parent,false)
        return ExampleViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val currentItem = items[position]
        if(context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            holder.nameandslug.text = context.resources.getString(R.string.name) + currentItem.name
        }else{
            holder.nameandslug.text = context.resources.getString(R.string.name_and_slug) + currentItem.name +" : "+ currentItem.slug
        }


        holder.dash.text = context.resources.getString(R.string.dash_characters) + currentItem.slug?.let { total_dash_character(it).toString() }
        holder.year.text = context.resources.getString(R.string.year) + currentItem.slug?.let { getYear(it) }
        if(currentItem.img!="") {
            Glide.with(holder.itemView.context).load(currentItem.img).into(holder.image)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
    fun updateData(data : List<dataList>){
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    private fun getYear(string: String): String {
        val splited: List<String> = string.split("-")
        return splited[splited.size - 1]
    }

    private fun total_dash_character(string: String): Int {
        var count = 0
        for (element in string) {
            if (element == '-') count++
        }
        return count
    }
}

class ExampleViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
    val nameandslug : TextView = itemView.findViewById(R.id.nameAndSlug)
    val year : TextView = itemView.findViewById(R.id.year)
    val image : ImageView = itemView.findViewById(R.id.productImage)
    val dash : TextView = itemView.findViewById(R.id.dash_character)
}