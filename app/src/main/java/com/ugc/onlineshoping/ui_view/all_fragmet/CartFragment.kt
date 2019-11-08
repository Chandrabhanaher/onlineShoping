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
import com.ugc.onlineshoping.data.CartItms
import com.ugc.onlineshoping.data.Cart_Model
import com.ugc.onlineshoping.freagment_adapter.Cart_Adapters
import com.ugc.onlineshoping.module.NetworkModule
import com.ugc.onlineshoping.module.gone
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartFragment : Fragment() {


    @BindView(R.id.listLoading)
    lateinit var loading_bar: ProgressBar

    @BindView(R.id.cart_items)
    lateinit var carts_items: RecyclerView

    var shoe_list :MutableList<CartItms> = ArrayList()

    lateinit var shoeAdapter: Cart_Adapters



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_cart, container, false)
        val userId = arguments!!.getInt("user_id")
        ButterKnife.bind(this,root)
        carts_items.setHasFixedSize(true)
        carts_items.layoutManager = GridLayoutManager(this.activity, 1,GridLayoutManager.VERTICAL, false)

        shoeAdapter = Cart_Adapters(shoe_list, this.context)
        carts_items.adapter = shoeAdapter

        show_cart_item_list(userId)
        return root
    }

    private fun show_cart_item_list(userId: Int) {
        val call : Call<Cart_Model> = NetworkModule.getClient.cart_list(userId)
        call.enqueue(object : Callback<Cart_Model>{
            override fun onFailure(call: Call<Cart_Model>?, t: Throwable?) {
                loading_bar.gone()
                if(t != null){
                    Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                }
            }
            override fun onResponse(call: Call<Cart_Model>?,response: Response<Cart_Model>?) {
                 loading_bar.gone()
                if(!response!!.body()?.error!!){
                    shoe_list.clear()
                    shoe_list.addAll(response.body()!!.cart_item)
                    shoeAdapter.notifyDataSetChanged()
                }else{
                    Toast.makeText(context, response.body()!!.message, Toast.LENGTH_SHORT).show()
                }

            }
        })
    }
}