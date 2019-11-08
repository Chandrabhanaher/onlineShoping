package com.ugc.onlineshoping.ui_view.all_fragmet

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.material.snackbar.Snackbar
import com.ugc.onlineshoping.R
import com.ugc.onlineshoping.ui_view.All_Shoe_List

class HomeFragment : Fragment(){
    private var mSnackBar: Snackbar? = null

    @BindView(R.id.goToList)
    lateinit var goToShop : Button

    @BindView(R.id.rootLayout)
    lateinit var rooLay : ConstraintLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_home, container, false)
        ButterKnife.bind(this,root)
        goToShop.setOnClickListener {

            checkConnecrion()
        }
        return root
    }
    @SuppressLint("WrongConstant")
    private fun checkConnecrion() {
         val cm  = context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
         val intWokInfo = cm.activeNetworkInfo
        if(intWokInfo != null && intWokInfo.isConnectedOrConnecting){
            startActivity(Intent(context, All_Shoe_List::class.java))
        }else{
            val messageToUser = "You are offline now." //TODO
            mSnackBar = Snackbar.make(rooLay, messageToUser, Snackbar.LENGTH_LONG)
            mSnackBar?.duration = Snackbar.LENGTH_LONG
            mSnackBar?.show()
        }
    }

}