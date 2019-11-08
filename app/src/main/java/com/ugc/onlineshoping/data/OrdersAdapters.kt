package com.ugc.onlineshoping.data

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ugc.onlineshoping.R
import com.ugc.onlineshoping.module.gone
import com.ugc.onlineshoping.module.showItems
import java.text.SimpleDateFormat
import java.util.*


class OrdersAdapters(private var orderList: MutableList<Booking_item>, var listener: EventClick?):RecyclerView.Adapter<OrdersAdapters.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.booking_cell_title, parent, false))
    }

    override fun getItemCount(): Int {
       return  orderList.size
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val my_order = orderList.get(position)
        var book_id: Int? = null
        book_id = my_order.book_id
        Glide.with(holder.itemView)
            .load(my_order.photo)
            .into(holder.imagesThum1)
        holder.materials1.text = my_order.title
        holder.totalAmount1.text = my_order.total_amount.toString()
        holder.totalQty1.text = "Qty :"+my_order.qty.toString()
        holder.blocking_address1.text  = "Delivery Address:"+my_order.delivery_address
        holder.booking_date1.text = "Order Date : "+my_order.date

        holder.returnPolicy1.text="Return Policy 4 Days"

        val return_item = my_order.return_order
        val cancel_item = my_order.cancel_order

        val r_date = my_order.return_date
        val c_date = my_order.cancel_date
        val del = my_order.confo_order
        val del_date = my_order.delivery_date
        val y: String = "Yes"

        when {
            del == y->{
                holder.cancel1.gone()
                if(return_item !=y){
                    val notD:String = "nn"
                    val rd:String  = "dd"
                    holder.countDate(del_date,notD,rd)
                }else{
                    holder.countDate(del_date,return_item,r_date)
                }
                holder.cancel_text.showItems()
            }
            cancel_item == y -> {
                holder.cancel1.gone()
                holder.return_items.gone()
                holder.cancel_text.showItems()
                holder.cancel_text.text = "Order has been canceled $c_date"
            }
            return_item == y -> {
                holder.cancel1.gone()
                holder.return_items.gone()
                holder.return_text.showItems()
                holder.return_text.text = "Item has been return $r_date"
            }
        }

        holder.return_items.setOnClickListener {
            listener!!.onRequestItemClickListener(orderList[position], position)
        }
        holder.cancel1.setOnClickListener {
            listener!!.onRequestItemCancelClickListener(orderList[position], position)
        }

    }

    class ViewHolder(itemView :View):RecyclerView.ViewHolder(itemView){
        fun countDate(delDate: String, return_item: String, rDate: String) {
            val d = Date()
            val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val cDate = format.format(d)

            var d1:Date? = null
            var d2:Date? = null
            try{
                d1 = format.parse(cDate)
                d2 = format.parse(delDate)

                val diff: Long = d1.time - d2.time

                val diffSeconds: Long = diff / 1000 % 60
                val diffMinutes: Long = diff / (60 * 1000) % 60
                val diffHours: Long = diff / (60 * 60 * 1000) % 24
                val diffDays: Long = diff / (24 * 60 * 60 * 1000)
                val y: String = "Yes"
                if(diffDays<=3){
                    if(return_item !=y){
                        return_items.showItems()
                        cancel_text.text = "Order has been delivered $delDate"
                    }else{
                        return_items.gone()
                        return_text.showItems()
                        cancel_text.text = "Order has been delivered $delDate"
                        return_text.text = "Item has been return $rDate"
                    }
                }else{
                    return_items.gone()
                    cancel_text.text = "Order has been delivered $delDate"
                }

            }catch (e: Exception){
                e.printStackTrace()
            }
        }

        val imagesThum1 : ImageView = itemView.findViewById(R.id.imagesThum)
        val materials1 : TextView = itemView.findViewById(R.id.materials)
        val totalAmount1 : TextView = itemView.findViewById(R.id.price)
        val totalQty1 : TextView = itemView.findViewById(R.id.totalQty)
        val blocking_address1 : TextView = itemView.findViewById(R.id.blocking_address)
        val booking_date1 : TextView = itemView.findViewById(R.id.booking_date)
        val returnPolicy1 : TextView = itemView.findViewById(R.id.returnPolicy)

        val return_items :Button = itemView.findViewById(R.id.return_item)
        val cancel1: Button = itemView.findViewById(R.id.cancel)

        val return_text: TextView = itemView.findViewById(R.id.return_text)
        val cancel_text: TextView = itemView.findViewById(R.id.cancel_text)

    }
    interface EventClick{
        fun onRequestItemClickListener(event: Booking_item, position: Int)
        fun onRequestItemCancelClickListener(event: Booking_item, position: Int)
    }
}