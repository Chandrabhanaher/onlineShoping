package com.ugc.onlineshoping.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ShowModel(
    @Expose
    @SerializedName("item_id")
    val item_id: Int,

    @Expose
    @SerializedName("photo")
    val photo: String,

    @Expose
    @SerializedName("price")
    val price: Double,

    @Expose
    @SerializedName("title")
    val title: String,

    @Expose
    @SerializedName("description")
    val description: String,

    @Expose
    @SerializedName("size")
    val size: String
)