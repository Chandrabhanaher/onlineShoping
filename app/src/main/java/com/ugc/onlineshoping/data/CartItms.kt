package com.ugc.onlineshoping.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CartItms(
    @Expose
    @SerializedName("cart_id")
    var cart_id: Int,

    @Expose
    @SerializedName("photo")
    var photo: String,

    @Expose
    @SerializedName("title")
    var title: String,

    @Expose
    @SerializedName("item_qty")
    var item_qty: Int,

    @Expose
    @SerializedName("price")
    var price: Double
)