package com.ugc.onlineshoping.data.admin

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ugc.onlineshoping.R
import com.ugc.onlineshoping.module.gone
import com.ugc.onlineshoping.module.showItems
import kotlinx.android.synthetic.main.my_cell.view.*
import java.util.*


class BookingAdapter(private var b_list: MutableList<Booking_Details>, var listener: EventClick):
    RecyclerView.Adapter<BookingAdapter.FloatingVH>() {
    private val unfoldedIndexes = HashSet<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FloatingVH {
        return FloatingVH(LayoutInflater.from(parent.context).inflate(R.layout.my_cell, null))
    }

    override fun getItemCount(): Int {
        return b_list.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FloatingVH, position: Int) {
        val b_items = b_list.get(position)

        with(holder.itemView){

            Glide.with(context)
                .load(b_items.photo)
                .into(sheImage)
            title.text = b_items.title
            totalAmount.text = "Total Amount: ₹"+b_items.total_amount.toString()
            totalQty.text = "Qty: "+b_items.qty.toString()
            c_name.text = b_items.name
            mobile.text = b_items.mobile
            email.text = b_items.email
            d_address.text = b_items.delivery_address
            d_date.text = "Booking Date : ${b_items.date}"

            val oredr_con = b_items.conform_order
            val d_date = b_items.delivery_date

            val return_order = b_items.return_order
            val re_date = b_items.return_date

            val order_cancel = b_items.cancel_order
            val ca_date = b_items.cancel_date

            val y:String = "Yes"

            when{

                return_order == y ->{
                    deliver.gone()
                    delivered_return.showItems()
                    delivered.gone()
                    delivered_cancel.gone()
                    delivered_return.text = "Return this item $re_date"
                }
                order_cancel == y ->{
                    deliver.gone()
                    delivered_cancel.showItems()
                    delivered.gone()
                    delivered_return.gone()
                    delivered_cancel.text = "Customer this item order has been canceled $ca_date"
                }
                oredr_con == y->{
                    deliver.gone()
                    delivered.showItems()
                    delivered_cancel.gone()
                    delivered_return.gone()
                    delivered.text = "Item has been delivered $d_date"
                }
            }
            if(return_order == y && oredr_con ==y){
                delivered.showItems()
                deliver.gone()
                delivered.text = "Item has been delivered $d_date"
                delivered_return.showItems()
                delivered_return.text = "Return this item $re_date"
            }

            deliver.setOnClickListener {
                listener.onRequestItemClickListener(b_list[position],position)

            }
        }
    }
    class FloatingVH(view: View):RecyclerView.ViewHolder(view) {

    }
}