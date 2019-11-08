package com.ugc.onlineshoping.ui_view.users

import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Paint
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.material.snackbar.Snackbar
import com.ugc.onlineshoping.R
import com.ugc.onlineshoping.ui_view.admins.Booking_List
import com.ugc.onlineshoping.data.LoginModel
import com.ugc.onlineshoping.module.NetworkModule
import com.ugc.onlineshoping.net_connection.ConnectivityReceiver
import com.ugc.onlineshoping.storage.ShearedPrfManager
import com.ugc.onlineshoping.ui_view.Shopping_Master
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@SuppressLint("Registered")
class User_Login : AppCompatActivity(), ConnectivityReceiver.ConnectivityReceiverListener{

    @BindView(R.id.email)
    lateinit var email_id: EditText

    @BindView(R.id.password)
    lateinit var password : EditText

    @BindView(R.id.signUp)
    lateinit var signUp : Button

    @BindView(R.id.newUser)
    lateinit var new_user : TextView

    private var mSnackBar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user__login)
        ButterKnife.bind(this)

        registerReceiver(ConnectivityReceiver(),
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

        new_user.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        signUp.setOnClickListener {
            val emailId = email_id.text.toString().trim()
            val pass = password.text.toString().trim()
            if (validate(email_id) && validate(password) && isValidEmail(emailId)) {
                signUp(emailId,pass)
            }
        }
        new_user.setOnClickListener {
            val i = Intent(applicationContext, User_Reg::class.java)
            startActivity(i);
        }

    }
    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        showMessage(isConnected)
    }
    @SuppressLint("WrongConstant")
    private fun showMessage(isConnected: Boolean) {

        if (!isConnected) {

            val messageToUser = "You are offline now." //TODO

            mSnackBar = Snackbar.make(findViewById(R.id.loginLayout), messageToUser, Snackbar.LENGTH_LONG)
            mSnackBar?.duration = Snackbar.LENGTH_INDEFINITE
            mSnackBar?.show()
        } else {
            val messageToUser = "You are online now." //TODO

            mSnackBar = Snackbar.make(findViewById(R.id.loginLayout), messageToUser, Snackbar.LENGTH_LONG)
            mSnackBar?.duration = Snackbar.LENGTH_INDEFINITE
            mSnackBar?.show()

            Handler().postDelayed(Runnable {
                mSnackBar?.dismiss()
            },1500)
        }


    }
    private fun signUp(emailId: String,pass: String){
        if(emailId == "admin@gmail.com" && pass == "admin"){
            startActivity(Intent(this, Booking_List::class.java))
            email_id.text.clear()
            password.text.clear()
        }else{
            NetworkModule.getClient.userLogin(emailId,pass)
                .enqueue(object : Callback<LoginModel> {
                    override fun onFailure(call: Call<LoginModel>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<LoginModel>, response: Response<LoginModel>) {
                        if(!response.body()?.error!!){
                            ShearedPrfManager.getInstance(applicationContext).saveUser(response.body()?.user!!)

                            Toast.makeText(applicationContext,response.body()?.message, Toast.LENGTH_SHORT).show()
                            email_id.text.clear()
                            password.text.clear()
                            val intent = Intent(applicationContext, Shopping_Master::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)

                        }else{
                            Toast.makeText(applicationContext,response.body()?.message, Toast.LENGTH_SHORT).show()
                        }
                    }
            })
        }
    }
    private fun isValidEmail(email: String): Boolean {
        return if( android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
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


    override fun onStart() {
        super.onStart()
        if(ShearedPrfManager.getInstance(this).isLoggedIn){
            val intent = Intent(applicationContext, Shopping_Master::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        ConnectivityReceiver.connectivityReceiverListener = this
    }

    override fun onResume() {
        super.onResume()
        ConnectivityReceiver.connectivityReceiverListener = this
    }

    override fun onPause() {
        super.onPause()
        ConnectivityReceiver.connectivityReceiverListener = this
    }

    override fun onStop() {
        super.onStop()
        ConnectivityReceiver.connectivityReceiverListener = this
    }

}
