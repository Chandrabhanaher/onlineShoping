package com.ugc.onlineshoping.ui_view.all_fragmet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.ugc.onlineshoping.R
import com.ugc.onlineshoping.data.OrderModel
import com.ugc.onlineshoping.data.OrdersAdapters
import com.ugc.onlineshoping.data.Booking_item
import com.ugc.onlineshoping.data.Data_Models
import com.ugc.onlineshoping.module.NetworkModule
import com.ugc.onlineshoping.module.gone
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class OrderFragment : Fragment(),OrdersAdapters.EventClick{


    @BindView(R.id.listLoading)
    lateinit var listLoad: ProgressBar

    @BindView(R.id.recycler_view)
    lateinit var shoeRecyclerView: RecyclerView

    var order_list: MutableList<Booking_item> = ArrayList()
    lateinit var orderAdapter: OrdersAdapters

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_order, container, false)
        val userId = arguments!!.getInt("user_id")

        ButterKnife.bind(this,root)

        shoeRecyclerView.setHasFixedSize(true)
        shoeRecyclerView.layoutManager = GridLayoutManager(context, 1,GridLayoutManager.VERTICAL, false)

        orderAdapter = OrdersAdapters(order_list,this)
        shoeRecyclerView.adapter = orderAdapter
        orderDetails(userId)

        return root
    }
    override fun onRequestItemClickListener(event: Booking_item, position: Int) {
        val b_id = event.book_id

        NetworkModule.getClient.return_order(b_id).enqueue(object : Callback<Data_Models>{
            override fun onFailure(call: Call<Data_Models>?, t: Throwable?) {
                if(t != null){
                    Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call<Data_Models>?, response: Response<Data_Models>?) {
                if(!response!!.body()?.error!!){
                    Toast.makeText(context, response.body()!!.message, Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(context, response.body()!!.message, Toast.LENGTH_SHORT).show()
                }
            }

        })

    }
    override fun onRequestItemCancelClickListener(event: Booking_item, position: Int){
        val b_id1 = event.book_id
        NetworkModule.getClient.cancel_order(b_id1).enqueue(object: Callback<Data_Models>{
            override fun onFailure(call: Call<Data_Models>?, t: Throwable?) {
                if(t != null){
                    Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                }
            }
            override fun onResponse(call: Call<Data_Models>?, response: Response<Data_Models>?) {
                if(!response!!.body()?.error!!){
                    Toast.makeText(context, response.body()!!.message, Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(context, response.body()!!.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun orderDetails(user_id: Int) {
         NetworkModule.getClient.myBooking(user_id)
             .enqueue(object : Callback<OrderModel>{
            override fun onFailure(call: Call<OrderModel>?, t: Throwable?) {
                listLoad.gone()
                if(t != null){
                    Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                }
            }
            override fun onResponse(call: Call<OrderModel>?,response: Response<OrderModel>?) {
                listLoad.gone()

                if(!response!!.body()?.error!!){
                    order_list.clear()
                    order_list.addAll(response.body()!!.booking_item)
                    orderAdapter.notifyDataSetChanged()
                }else{
                    Toast.makeText(context, response.body()!!.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}