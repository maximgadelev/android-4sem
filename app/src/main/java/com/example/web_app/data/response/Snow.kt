package com.example.web_app.data.response

import com.google.gson.annotations.SerializedName

data class Snow(
    @SerializedName("1h")
    val h: Double
)