package com.ugc.onlineshoping.storage

import android.content.Context
import android.content.Intent
import com.ugc.onlineshoping.ui_view.users.User_Login

class ShearedPrfManager private constructor(private val mCtx : Context){

    val isLoggedIn: Boolean
    get() {
        val shearedPreference= mCtx.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE)
        return shearedPreference.getInt("id",-1)!= -1
    }

    val user: User
        get() {
                val shearedPreference= mCtx.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE)
                return User(
                    shearedPreference.getInt("id",-1),
                    shearedPreference.getString("name",null).toString(),
                    shearedPreference.getString("mobile",null).toString(),
                    shearedPreference.getString("email",null).toString(),
                    shearedPreference.getString("address",null).toString()
                )
            }
    fun saveUser(user: User){
        val shearedPreference= mCtx.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE)
        val editor= shearedPreference.edit()

        editor.putInt("id",user.id)
        editor.putString("name", user.name)
        editor.putString("mobile", user.mobile)
        editor.putString("email", user.email)
        editor.putString("address", user.address)
        editor.apply()
        editor.commit()
    }
    fun clear(){
        val shearedPreference= mCtx.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE)
        val editor= shearedPreference.edit()
        editor.clear()
        editor.apply()
        editor.commit()
        mCtx.startActivity(Intent(mCtx, User_Login::class.java))
    }
    companion object{
        private val PREF_NAME : String  = "shopping"
        private var mInstances: ShearedPrfManager? = null
        @Synchronized
        fun getInstance(mCtx: Context): ShearedPrfManager{
            if(mInstances == null){
                mInstances = ShearedPrfManager(mCtx)
            }
            return mInstances as ShearedPrfManager
        }
    }
}

