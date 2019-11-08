package com.ugc.onlineshoping.ui_view

import androidx.appcompat.app.AppCompatActivity
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.baianat.floadingcellanimationsample.utils.GridSpacingItemDecoration
import com.baianat.floadingcellanimationsample.utils.ViewUtils
import com.ramotion.foldingcell.FoldingCell
import com.ugc.onlineshoping.R
import com.ugc.onlineshoping.data.Data_Models
import com.ugc.onlineshoping.data.FloadingAdapter
import com.ugc.onlineshoping.data.MyOrder
import com.ugc.onlineshoping.data.ShowModel
import com.ugc.onlineshoping.module.NetworkModule
import com.ugc.onlineshoping.module.gone
import com.ugc.onlineshoping.storage.ShearedPrfManager
import com.ugc.onlineshoping.ui_view.users.User_Address
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import java.util.ArrayList

class All_Shoe_List : AppCompatActivity(), FloadingAdapter.EventClick {

    @BindView(R.id.listLoading)
    lateinit var listLoad: ProgressBar

    @BindView(R.id.recycler_view)
    lateinit var shoeRecyclerView: RecyclerView

    var shoe_list :MutableList<ShowModel> = ArrayList()

    lateinit var floating_Adapter: FloadingAdapter
    var searchView: SearchView?= null

    var u_id: Int? = null
    var email:String? = null
    var address: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        ButterKnife.bind(this)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title="Shoes"

        shoeRecyclerView.setHasFixedSize(true)
        shoeRecyclerView.layoutManager = GridLayoutManager(this, 1)

        shoeRecyclerView.addItemDecoration(
            GridSpacingItemDecoration(1,ViewUtils.dpToPx(12F),true,0)
        )

        floating_Adapter = FloadingAdapter(shoe_list,this)
        shoeRecyclerView.adapter = floating_Adapter

        getListData()

        u_id = ShearedPrfManager.getInstance(this).user.id
        email = ShearedPrfManager.getInstance(this).user.email
        address = ShearedPrfManager.getInstance(this).user.address
    }

    private fun getListData() {
        val call : Call<List<ShowModel>> = NetworkModule.getClient.getShoe()
        call.enqueue(object :Callback<List<ShowModel>>{
            override fun onResponse(call: Call<List<ShowModel>>?, response: Response<List<ShowModel>>?) {
                listLoad.gone()
                shoe_list.clear()
                shoe_list.addAll(response!!.body()!!)
                floating_Adapter.notifyDataSetChanged()
            }
            override fun onFailure(call: Call<List<ShowModel>>?, t: Throwable?) {
                listLoad.gone()
                if (t != null) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
                }
            }})

    }
    override fun onEventClickListener(event: ShowModel, position: Int, view: View) {
        (view as FoldingCell).toggle(false)
        // register in adapter that state for selected cell is toggled
        floating_Adapter.registerToggle(position)
    }

    override fun orderNow(event: ShowModel,position: Int, qty: Int) {
        val user_id = u_id!!.toInt()
        val item_id  = event.item_id
        val price = event.price
        checkDeliveryAddress(user_id,item_id,price,qty)
    }
//  Check Delivery Address
    private fun checkDeliveryAddress(userId: Int, item_id: Int, price: Double,qty: Int) {
        NetworkModule.getClient.checkUserAddress(userId)
            .enqueue(object : Callback<Data_Models>{
                override fun onFailure(call: Call<Data_Models>?, t: Throwable?) {
                    if(t != null){
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onResponse(call: Call<Data_Models>?, response: Response<Data_Models>?) {
                    if(!response!!.body()?.error!!){
                        conformDelivery(userId,item_id,price,qty)
                    }else{
                        Toast.makeText(applicationContext,response.body()!!.message,Toast.LENGTH_SHORT).show()
                        startActivity(Intent(applicationContext, User_Address::class.java))
                    }
                }

            })
    }

    //  Delivery Conform
    private fun conformDelivery(userId: Int, item_id: Int, price: Double,qty: Int) {
        val item_qty : Int = qty
        val d_cahrge: Double = 49.00 * qty
        val total_price: Double = price * item_qty
        val total_amount: Double = total_price + d_cahrge
        val d_address = address.toString()

        NetworkModule.getClient.myOrder(userId,item_id,item_qty,d_cahrge,total_price,total_amount,d_address)
            .enqueue(object : Callback<MyOrder>{
                override fun onFailure(call: Call<MyOrder>?, t: Throwable?) {
                    if(t != null){
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onResponse(call: Call<MyOrder>?, response: Response<MyOrder>?) {
                    if(!response!!.body()?.error!!){
                        Toast.makeText(applicationContext, response.body()!!.message, Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(applicationContext, response.body()!!.message, Toast.LENGTH_SHORT).show()
                    }
                }
            })
    }
//  Add to cart
    override fun onRequestItemClickListener(event: ShowModel, position: Int) {
        val item_id  = event.item_id
        val user_id = u_id!!.toInt()
        val item_qty :Int = 1
        val price = event.price

        NetworkModule.getClient.addCart(item_id,user_id,item_qty,price)
            .enqueue(object : Callback<Data_Models>{
                override fun onFailure(call: Call<Data_Models>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
                }
                override fun onResponse(call: Call<Data_Models>, response: Response<Data_Models>) {
                   Toast.makeText(applicationContext, response.body()!!.message, Toast.LENGTH_SHORT).show()
                }
            })
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        val searchManager : SearchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu!!.findItem(R.id.item_search).actionView as SearchView
        searchView!!.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView!!.maxWidth = Int.MAX_VALUE

        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                floating_Adapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                floating_Adapter.filter.filter(query)
                return false
            }
        })
        return  true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return if(id == R.id.item_search) {
            true
        }else{
            super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        if(!searchView!!.isIconified){
            searchView!!.isIconified = true
            return
        }
        super.onBackPressed()
    }
}
