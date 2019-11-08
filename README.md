# Online Shopping
Using for Android Kotlin

# PHP Web services using Codeigniter 
```
  Codeigniter 3.1.9
  XAMP 7.2
  PHP Version 7.2.6
  MYSQl 5.0.12-dev
```

#Kotlin Version 1.3.50

## Using for Retrofit And RXJava /Rx Android / OKHttp / HttpLoggingInterceptor / Folding animation / Glide / ButterKnife Dependencies
```
    implementation 'com.squareup.retrofit2:retrofit:2.6.2'
    implementation 'com.squareup.retrofit2:adapter-rxjava2:2.6.2'
    implementation 'com.squareup.retrofit2:converter-gson:2.6.2'
    
    implementation "io.reactivex.rxjava2:rxandroid:2.1.1"
    implementation "io.reactivex.rxjava2:rxjava:2.2.7"
    
    implementation 'com.squareup.okhttp3:okhttp:4.2.2'
    implementation 'com.squareup.okhttp3:logging-interceptor:4.2.2'
    
    implementation 'com.ramotion.foldingcell:folding-cell:1.2.3'
    
    implementation 'com.github.bumptech.glide:glide:4.10.0'
    
    implementation 'com.jakewharton:butterknife:10.2.0'
    kapt 'com.jakewharton:butterknife-compiler:10.2.0'
   
```
## Using Internet Connetivity BroadcastReceiver with receiver register in  Manifest file
```
  Communicate the android OS to Application check the internet connection status
    
      <receiver
            android:name=".net_connection.ConnectivityReceiver"
            android:enabled="true">
            <intent-filter>
                <action android:name="android.net.conn.CONNECTIVITY_CHANGE" />
            </intent-filter>
        </receiver>
 ```    
## Show Shopping Cart with added cart count using Notification badge
```
Dependencies

  implementation 'com.nex3z:notification-badge:1.0.2'

* Set notification count 
* Sample code

    object SetNotificationCount {
        fun setBadgeCount(context: Context, icon: LayerDrawable, count: Int) {

            val badge: BadgeDrawable

            // Reuse drawable if possible
            val reuse = icon.findDrawableByLayerId(R.id.ic_badge)
            if (reuse != null && reuse is BadgeDrawable) {
                badge = reuse
            } else {
                badge = BadgeDrawable(context)
            }

            badge.setCount(count)
            icon.mutate()
            icon.setDrawableByLayerId(R.id.ic_badge, badge)
        }
    }
```
## Singleton Class in ShearedPrfManager (Store the user data in this class)
```
A singleton class is a class that is defined in such a way that only one instance of the class can 
be created and is used where we need only one instance of the class like in logging, database connections, thread pool, etc
```
## Companion Object
```
can implement a companion object, which is an object that is common to all instances of that class. 
It'd come to be similar to static fields in Java. 
In this case I'm creating a class that extends Application and stores it in the companion object its unique instance
```
## Permission in Android Manifest file.
```
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.INTERNET" />
```
## Singletone Class code 
```
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
```

   
