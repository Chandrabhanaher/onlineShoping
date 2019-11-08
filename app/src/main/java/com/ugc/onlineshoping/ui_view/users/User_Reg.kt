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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class User_Reg : AppCompatActivity() {

    @BindView(R.id.username)
    lateinit var name : EditText

    @BindView(R.id.mobile)
    lateinit var mobile_no : EditText

    @BindView(R.id.email)
    lateinit var email_id: EditText

    @BindView(R.id.password)
    lateinit var password : EditText

    @BindView(R.id.signUp)
    lateinit var signUp : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user__reg)
        ButterKnife.bind(this)

        signUp.setOnClickListener {
            val user_name = name.text.toString().trim()
            val emailId = email_id.text.toString().trim()
            val pass = password.text.toString().trim()
            val m_number = mobile_no.text.toString().trim()


            if (validate(name) && validate(email_id) && isValidEmail(emailId) && validate(password) && validate(mobile_no)) {
                if(check_Length(m_number)){
                    signUp(user_name,m_number,emailId,pass)
                }
            }
        }
    }

    private fun check_Length(mobNumber: String): Boolean {
        return if(mobNumber.length <= 10){
            true
        }else{
            mobile_no.error ="Enter Valid mobile number"
            false
        }
    }

    private fun signUp(user_name:String,mobile:String,emailId:String,pass:String){
       NetworkModule.getClient.createUser(user_name,mobile,emailId,pass)
           .enqueue(object : Callback<Data_Models>{
               override fun onResponse(call: Call<Data_Models>, response: Response<Data_Models>) {
                   if(!response.body()?.error!!){
                       Toast.makeText(applicationContext,response.body()?.message, Toast.LENGTH_SHORT).show()

                       val intent = Intent(applicationContext, User_Address::class.java)
                       intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                       intent.putExtra("emailId",emailId)
                       startActivity(intent)
                   }else{
                       Toast.makeText(applicationContext,response.body()?.message, Toast.LENGTH_SHORT).show()
                   }

               }

               override fun onFailure(call: Call<Data_Models>, t: Throwable) {
                   Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
               }
           })

    }
    private fun isValidEmail(email: String): Boolean {
        return if(android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            true
        }else{
            email_id.error="Enter valid email"
            false
        }
    }

    private fun validate(editText: EditText): Boolean {
        if (editText.text.toString().trim().isNotEmpty()) {
            return true
        }
        editText.error = "Please Fill This";
        editText.requestFocus()
        return false
    }


}
