package com.ugc.onlineshoping.ui_view.users

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.ugc.onlineshoping.R
import com.ugc.onlineshoping.data.Data_Models
import com.ugc.onlineshoping.module.NetworkModule
import com.ugc.onlineshoping.storage.ShearedPrfManager
import com.ugc.onlineshoping.ui_view.All_Shoe_List
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class User_Address : AppCompatActivity() {
    var u_id: Int? = null

    @BindView(R.id.etUserInput)
    lateinit var userAddress : EditText

    @BindView(R.id.submit)
    lateinit var btnSubmit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user__address)
        ButterKnife.bind(this)
        u_id = ShearedPrfManager.getInstance(this).user.id

        var email = intent.getStringExtra("emailId")

        btnSubmit.setOnClickListener {
            val address = userAddress.text.toString()
            val userId = email
            if(isvalid(address)){
                val call  = NetworkModule.getClient.newAddress(userId,address)
                call.enqueue(object: Callback<Data_Models>{
                    override fun onFailure(call: Call<Data_Models>?, t: Throwable?) {
                        if(t != null){
                            Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                    override fun onResponse(
                        call: Call<Data_Models>?,
                        response: Response<Data_Models>?) {
                        val msg = response!!.body()!!.message
                        if(!response.body()?.error!!){
                            Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
                            ShearedPrfManager.getInstance(applicationContext).clear()
                        }else{
                            Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
                        }
                    }

                })
            }
        }
    }

    private fun isvalid(address: String): Boolean {
        if(address.isNotEmpty()){
            return true
        }
        userAddress.error = "Enter Address"
        return false
    }
}

