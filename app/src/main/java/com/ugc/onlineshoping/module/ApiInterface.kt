package com.ugc.onlineshoping.module

import com.ugc.onlineshoping.data.*
import com.ugc.onlineshoping.data.admin.Booking_Details
import com.ugc.onlineshoping.storage.User
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiInterface {

    @FormUrlEncoded
    @POST("new_user")
    fun createUser(
        @Field("name")name:String,
        @Field("mobile")mobile:String,
        @Field("email")email:String,
        @Field("pass")pass:String
    ):Call<Data_Models>


    @FormUrlEncoded
    @POST("login_hear")
    fun userLogin(
        @Field("email")email:String,
        @Field("pass")pass:String
    ):Call<LoginModel>


    @GET("items")
    fun getShoe():Call<List<ShowModel>>

    @FormUrlEncoded
    @POST("add_cart")
    fun addCart(
        @Field("item_id")item_id:Int,
        @Field("user_id")user_id:Int,
        @Field("item_qty")item_qty:Int,
        @Field("total_price")total_price:Double
    ):Call<Data_Models>

    @FormUrlEncoded
    @POST("cart_item_list")
    fun cart_list(
        @Field("user_id")user_id:Int
    ):Call<Cart_Model>

    @FormUrlEncoded
    @POST("count_items")
    fun countCart(
        @Field("user_id")user_id:Int
    ):Call<CountModel>

    @FormUrlEncoded
    @POST("my_booking")
    fun myBooking(
        @Field("user_id")user_id:Int
    ):Call<OrderModel>

    @FormUrlEncoded
    @POST("d_address")
    fun checkUserAddress(@Field("user_id")user_id:Int):Call<Data_Models>

    @FormUrlEncoded
    @POST("delivery")
    fun myOrder(
        @Field("user_id")user_id:Int,
        @Field("item_id")item_id:Int,
        @Field("item_qty")item_qty:Int,
        @Field("delivery_charge")delivery_charge:Double,
        @Field("total_price")total_price:Double,
        @Field("total_amount")total_amount:Double,
        @Field("delivery_address")delivery_address:String
    ):Call<MyOrder>

    @FormUrlEncoded
    @POST("enter_address")
    fun newAddress(
        @Field("email")email:String,
        @Field("address")address:String
    ):Call<Data_Models>

    @GET("booking")
    fun booking_details():Call<List<Booking_Details>>

    @FormUrlEncoded
    @POST("conform_order")
    fun order_comnform(
        @Field("b_id")b_id:Int
    ):Call<Data_Models>

    @FormUrlEncoded
    @POST("return_order")
    fun return_order(
        @Field("b_id")b_id:Int
    ):Call<Data_Models>

    @FormUrlEncoded
    @POST("cancel_order")
    fun cancel_order(
        @Field("b_id")b_id:Int
    ):Call<Data_Models>


    @FormUrlEncoded
    @POST("userprofile")
    fun myProfile(
        @Field("user_id")user_id:Int
    ):Call<User_Profile>
}