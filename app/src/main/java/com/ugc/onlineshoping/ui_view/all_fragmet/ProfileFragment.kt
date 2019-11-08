package com.ugc.onlineshoping.ui_view.all_fragmet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import butterknife.BindView
import butterknife.ButterKnife
import com.ugc.onlineshoping.R
import com.ugc.onlineshoping.data.User_Profile
import com.ugc.onlineshoping.module.NetworkModule
import com.ugc.onlineshoping.storage.ShearedPrfManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {

    @BindView(R.id.name)
    lateinit var user_name: TextView

    @BindView(R.id.mobile)
    lateinit var mob: TextView

    @BindView(R.id.email)
    lateinit var email_id: TextView

    @BindView(R.id.address)
    lateinit var add: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        ButterKnife.bind(this,root)
        val user_id = arguments!!.getInt("user_id")
        showProfile(user_id)
        return root
    }

    private fun showProfile(userId: Int) {
        NetworkModule.getClient.myProfile(userId).enqueue(object: Callback<User_Profile>{
            override fun onFailure(call: Call<User_Profile>, t: Throwable?) {
                if(t != null){
                    Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call<User_Profile>?, response: Response<User_Profile>?) {
                if(!response!!.body()?.error!!){
                    val name = response.body()!!.user.name
                    val mobile = response.body()!!.user.mobile
                    val email  = response.body()!!.user.email
                    val address = response.body()!!.user.address

                    user_name.text = name
                    mob.text = mobile.toString()
                    email_id.text = email
                    add.text = address
                }else{
                    Toast.makeText(context, response.body()!!.message, Toast.LENGTH_SHORT).show()
                }
            }

        })
    }
}