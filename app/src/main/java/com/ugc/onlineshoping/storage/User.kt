package com.ugc.onlineshoping.storage

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class User(
    @Expose
    @SerializedName("id")
    val id :Int,

    @Expose
    @SerializedName("name")
    val name: String,

    @Expose
    @SerializedName("mobile")
    val mobile: String,

    @Expose
    @SerializedName("email")
    val email: String,

    @Expose
    @SerializedName("address")
    val address: String
)