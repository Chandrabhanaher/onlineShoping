package com.ugc.onlineshoping.ui_view

import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import com.google.android.material.snackbar.Snackbar
import androidx.navigation.findNavController
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.GravityCompat
import androidx.navigation.ui.*
import biz.gina.southernbreezetour.ui.view.notification.NotificationCountSetClass
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nex3z.notificationbadge.NotificationBadge
import com.ugc.onlineshoping.R
import com.ugc.onlineshoping.data.CountModel
import com.ugc.onlineshoping.module.NetworkModule
import com.ugc.onlineshoping.module.gone
import com.ugc.onlineshoping.module.showItems
import com.ugc.onlineshoping.net_connection.ConnectivityReceiver
import com.ugc.onlineshoping.storage.ShearedPrfManager
import com.ugc.onlineshoping.ui_view.users.User_Login
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@SuppressLint("Registered")
class Shopping_Master : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener,
    ConnectivityReceiver.ConnectivityReceiverListener,
    BottomNavigationView.OnNavigationItemSelectedListener {

    private var mSnackBar: Snackbar? = null

    private lateinit var appBarConfiguration: AppBarConfiguration
    var u_id: Int?= null
    var email:String?= null
    var drawerLayout: DrawerLayout? = null
    var badge : NotificationBadge? = null

    var itemCount = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping__master)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        registerReceiver(ConnectivityReceiver(),
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )

        u_id = ShearedPrfManager.getInstance(this).user.id
        email = ShearedPrfManager.getInstance(this).user.email

        drawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController by lazy { findNavController(R.id.nav_host_fragment) }
        val appBarConfiguration by lazy {
            AppBarConfiguration(
                setOf(
                    R.id.nav_home, R.id.nav_order, R.id.nav_cart,
                    R.id.nav_profile
                ), drawerLayout
            )
        }


        NavigationUI.setupActionBarWithNavController(this,navController,drawerLayout)
        NavigationUI.setupWithNavController(toolbar,navController,appBarConfiguration)

        navController.addOnDestinationChangedListener{_, destination, _ ->

        }

        navView.setNavigationItemSelectedListener(this)

        cartCount(u_id!!)

        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_nav)
        bottomNav.setOnNavigationItemSelectedListener(this)
    }
    @SuppressLint("WrongConstant")
    private fun showMessage(isConnected: Boolean) {

        if (!isConnected) {

            val messageToUser = "You are offline now." //TODO

            mSnackBar = Snackbar.make(findViewById(R.id.rootLayout), messageToUser, Snackbar.LENGTH_LONG) //Assume "rootLayout" as the root layout of every activity.
            mSnackBar?.duration = Snackbar.LENGTH_INDEFINITE
            mSnackBar?.show()
        } else {
            val messageToUser = "You are online now." //TODO

            mSnackBar = Snackbar.make(findViewById(R.id.rootLayout), messageToUser, Snackbar.LENGTH_LONG) //Assume "rootLayout" as the root layout of every activity.
            mSnackBar?.duration = Snackbar.LENGTH_INDEFINITE
            mSnackBar?.show()

            Handler().postDelayed(Runnable {
                mSnackBar?.dismiss()
            },1500)
        }


    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        showMessage(isConnected)
    }

    private fun cartCount(user_id:Int) {
        NetworkModule.getClient.countCart(user_id)
            .enqueue(object: Callback<CountModel>{
                override fun onFailure(call: Call<CountModel>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
                }
                override fun onResponse(call: Call<CountModel>, response: Response<CountModel>) {
                    itemCount = response.body()!!.item_count
                }
            })
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        val bundle= bundleOf("user_id" to   u_id)
        val navController by lazy { findNavController(R.id.nav_host_fragment) }
        when(p0.itemId){
            R.id.nav_home->{
                navController.navigate(R.id.nav_home,bundle)
            }
            R.id.nav_order->{
                navController.navigate(R.id.nav_order,bundle)
            }
            R.id.nav_cart->{
                navController.navigate(R.id.nav_cart,bundle)
            }
            R.id.nav_profile->{
                navController.navigate(R.id.nav_profile,bundle)
            }
            R.id.lot_out->{
                logout()
            }
            R.id.navigation_home->{
                startActivity(Intent(this, All_Shoe_List::class.java))
            }
        }
        drawerLayout!!.closeDrawer(GravityCompat.START)
        return  true
    }

    private fun logout() {
        ShearedPrfManager.getInstance(this).clear()
    }


    override fun onResume() {
        super.onResume()
        if(!ShearedPrfManager.getInstance(this).isLoggedIn){
            val intent = Intent(applicationContext, User_Login::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            countItems()
        }
        ConnectivityReceiver.connectivityReceiverListener = this
    }

    override fun onStart() {
        super.onStart()
        countItems()
        ConnectivityReceiver.connectivityReceiverListener = this
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.shopping__master, menu)
        /*var item = menu.findItem(R.id.cart_icon).actionView
        badge =  item.findViewById(R.id.badge)
        updateCartCount()*/

        var item = menu!!.findItem(R.id.item_cart)
        NotificationCountSetClass.setAddToCart(applicationContext, item, itemCount!!)
        invalidateOptionsMenu()
        countItems()
        return true
    }

    private fun updateCartCount() {
        if(badge == null)true
        runOnUiThread {
            Runnable {
                if (itemCount == 0){
                    badge!!.gone()
                }else {
                    badge!!.showItems()
                    badge!!.setText(itemCount.toString())
                }
            }
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        var item = menu!!.findItem(R.id.item_cart)
        NotificationCountSetClass.setAddToCart(applicationContext, item, itemCount!!)
        invalidateOptionsMenu()
        countItems()
        return super.onPrepareOptionsMenu(menu)
    }

    private fun countItems() {
        NotificationCountSetClass.setNotifyCount(itemCount)
        invalidateOptionsMenu()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val bundle= bundleOf("user_id" to   u_id)
        val navController by lazy { findNavController(R.id.nav_host_fragment) }
        when(item.itemId){
            R.id.item_notes->{

            }
            R.id.item_cart->{
                navController.navigate(R.id.nav_cart,bundle)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
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

