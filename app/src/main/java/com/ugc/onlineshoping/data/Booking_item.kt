package com.ugc.onlineshoping.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Booking_item (
    @Expose
    @SerializedName("book_id")
    val book_id: Int,

    @Expose
    @SerializedName("photo")
    val photo: String,

    @Expose
    @SerializedName("title")
    val title: String,

    @Expose
    @SerializedName("qty")
    val qty: Int,

    @Expose
    @SerializedName("delivery_charge")
    val delivery_charge: Int,

    @Expose
    @SerializedName("total_amount")
    val total_amount: Double,

    @Expose
    @SerializedName("delivery_address")
    val delivery_address: String,

    @Expose
    @SerializedName("date")
    val date: String,

    @Expose
    @SerializedName("return_order")
    val return_order: String,

    @Expose
    @SerializedName("cancel_order")
    val cancel_order: String,

    @Expose
    @SerializedName("return_date")
    val return_date: String,

    @Expose
    @SerializedName("cancel_date")
    val cancel_date: String,

    @Expose
    @SerializedName("confo_order")
    val confo_order: String,

    @Expose
    @SerializedName("delivery_date")
    val delivery_date: String

)