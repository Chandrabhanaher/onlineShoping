package com.ugc.onlineshoping.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CountModel (

    @Expose
    @SerializedName("item_count")
    var item_count: Int

)