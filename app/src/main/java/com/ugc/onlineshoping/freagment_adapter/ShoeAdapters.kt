package com.ugc.onlineshoping.freagment_adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ugc.onlineshoping.R
import com.ugc.onlineshoping.data.ShowModel
import com.ugc.onlineshoping.ui_view.All_Shoe_List

class ShoeAdapters(private var shoe_list: MutableList<ShowModel>, private var context: Context?):
    RecyclerView.Adapter<ShoeAdapters.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.gird_items, parent, false))
    }

    override fun getItemCount(): Int {
       return shoe_list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataModel= shoe_list.get(position)

        context?.let {
            Glide.with(it)
                .load(dataModel.photo)
                .into(holder.imageView)
        }
        holder.titleText.text = dataModel.title
        var p: Double = dataModel.price
        holder.priceText.text = p.toString()
        holder.imageView.setOnClickListener {
            val i = Intent(context, All_Shoe_List::class.java)
            this.context!!.startActivities(arrayOf(i))
        }
    }

    class ViewHolder(itemView : View):RecyclerView.ViewHolder(itemView){
        var imageView : ImageView = itemView.findViewById(R.id.imagesThum)
        var titleText : TextView = itemView.findViewById(R.id.materials)
        var priceText : TextView = itemView.findViewById(R.id.price)
    }
}