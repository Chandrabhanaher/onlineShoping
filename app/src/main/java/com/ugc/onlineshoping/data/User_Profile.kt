package com.ugc.onlineshoping.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.ugc.onlineshoping.storage.User

data class User_Profile (
    @Expose
    @SerializedName("user")
    val user: User,

    @Expose
    @SerializedName("error")
    val error: Boolean,

    @Expose
    @SerializedName("message")
    val message: String

)