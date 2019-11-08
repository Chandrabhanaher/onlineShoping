package com.ugc.onlineshoping.freagment_adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ugc.onlineshoping.R
import com.ugc.onlineshoping.data.CartItms

class Cart_Adapters(private var shoe_list: MutableList<CartItms>, private var context: Context?):
    RecyclerView.Adapter<Cart_Adapters.ViewHolder>(){

    var qty :Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.cart_items, parent, false))
    }

    override fun getItemCount(): Int {
       return shoe_list.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cart_items = shoe_list.get(position)
        val cart_id = cart_items.cart_id
        context?.let {
            Glide.with(it)
                .load(cart_items.photo)
                .into(holder.item_image)
        }
        holder.item_title.text = cart_items.title
        val p: Double = cart_items.price
        holder.item_price.text = "₹$p"

        val qty_array = arrayOf<String>("1","2","3","4","5")
        val adapters = ArrayAdapter(context!!, android.R.layout.simple_spinner_item,qty_array)
        holder.qty_spinner.adapter = adapters
        holder.qty_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
            override fun onItemSelected(parent: AdapterView<*>?,view: View?,position: Int,id: Long) {
                val qtys = parent!!.getItemAtPosition(position).toString().trim()
                qty = qtys.toInt()

                holder.prices.text ="Price( "+ qty+" item)"
                val qty_price : Double = qty!! * p
                holder.item_price1.text = "₹"+qty_price.toString()

                val d:Int = 49 * qty!!
                holder.d_charge.text = "₹$d"
                val amount: Double = qty_price + d
                holder.total_price.text = amount.toString()
//                holder.item_price.text = "₹$amount"
            }
        }
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val item_image = itemView.findViewById<ImageView>(R.id.item_image)
        val item_title = itemView.findViewById<TextView>(R.id.item_title)
        val item_price = itemView.findViewById<TextView>(R.id.item_price)
        val qty_spinner = itemView.findViewById<Spinner>(R.id.qty_spinner)
        val prices = itemView.findViewById<TextView>(R.id.prices)
        val item_price1 = itemView.findViewById<TextView>(R.id.item_price1)
        val d_charge = itemView.findViewById<TextView>(R.id.d_charge)
        val total_price = itemView.findViewById<TextView>(R.id.total_price)
    }
}