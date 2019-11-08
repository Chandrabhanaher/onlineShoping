package com.ugc.onlineshoping.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data  class Data_Models (

    @Expose
    @SerializedName("error")
    val error: Boolean,

    @Expose
    @SerializedName("message")
    val message: String
)