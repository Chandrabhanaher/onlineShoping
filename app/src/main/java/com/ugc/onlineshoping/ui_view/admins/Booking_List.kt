package com.ugc.onlineshoping.ui_view.admins

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.ugc.onlineshoping.R

import com.ugc.onlineshoping.data.Data_Models
import com.ugc.onlineshoping.data.admin.BookingAdapter
import com.ugc.onlineshoping.data.admin.Booking_Details
import com.ugc.onlineshoping.module.NetworkModule
import com.ugc.onlineshoping.module.gone
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.ugc.onlineshoping.data.admin.EventClick as EventClick

class Booking_List : AppCompatActivity(), EventClick {


    @BindView(R.id.listLoading)
    lateinit var process: ProgressBar

    @BindView(R.id.recycler_view)
    lateinit var booking_list: RecyclerView

    var bookingList : MutableList<Booking_Details> = ArrayList()
    lateinit var booking_adapter : BookingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking__list)
        ButterKnife.bind(this)

        booking_list.setHasFixedSize(true)
        booking_list.layoutManager = GridLayoutManager(this, 1)

        booking_adapter = BookingAdapter(bookingList, this)
        booking_list.adapter = booking_adapter
        showBookingDetails()
    }

    private fun showBookingDetails() {
        val call: Call<List<Booking_Details>> = NetworkModule.getClient.booking_details()
        call.enqueue(object: Callback<List<Booking_Details>>{
            override fun onFailure(call: Call<List<Booking_Details>>?, t: Throwable?) {
                process.gone()
                if(t!= null){
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
                }
            }
            override fun onResponse(
                call: Call<List<Booking_Details>>?, response: Response<List<Booking_Details>>?) {
                process.gone()
                bookingList.clear()
                bookingList.addAll(response!!.body()!!)
                booking_adapter.notifyDataSetChanged()
            }
        })
    }

    override fun onRequestItemClickListener(event: Booking_Details, position: Int) {
        val b_id = event.book_id
        NetworkModule.getClient.order_comnform(b_id)
            .enqueue(object : Callback<Data_Models>{
                override fun onFailure(call: Call<Data_Models>?, t: Throwable?) {
                    if (t != null){
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onResponse(call: Call<Data_Models>?, response: Response<Data_Models>?) {
                    if(!response!!.body()?.error!!){
                        Toast.makeText(applicationContext,response.body()!!.message, Toast.LENGTH_SHORT).show()
                        booking_adapter.notifyDataSetChanged()
                    }else{
                        Toast.makeText(applicationContext,response.body()!!.message, Toast.LENGTH_SHORT).show()
                        booking_adapter.notifyDataSetChanged()
                    }
                }

            })
    }
    var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if(doubleBackToExitPressedOnce){
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()
        Handler().postDelayed(Runnable {
            doubleBackToExitPressedOnce=false
        },2000)
    }


}
