package com.ugc.onlineshoping.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class OrderModel (
    @Expose
    @SerializedName("error")
    val error: Boolean,

    @Expose
    @SerializedName("message")
    val message: String,

    @Expose
    @SerializedName("booking_item")
    val booking_item: List<Booking_item>
)