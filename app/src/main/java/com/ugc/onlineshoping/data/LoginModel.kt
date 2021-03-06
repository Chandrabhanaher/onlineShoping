package com.ugc.onlineshoping.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.ugc.onlineshoping.storage.User

data class LoginModel(
    @Expose
    @SerializedName("error")
    val error: Boolean,

    @Expose
    @SerializedName("message")
    val message: String,

    @Expose
    @SerializedName("user")
    val user: User
)