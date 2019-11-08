package com.ugc.onlineshoping.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Cart_Model(
    @Expose
    @SerializedName("error")
    val error: Boolean,

    @Expose
    @SerializedName("message")
    val message: String,

    @Expose
    @SerializedName("cart_item")
    val cart_item: List<CartItms>
)