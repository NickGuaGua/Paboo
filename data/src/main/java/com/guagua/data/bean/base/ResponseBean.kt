package com.guagua.data.bean.base

import com.google.gson.annotations.SerializedName

abstract class ResponseBean {
    @SerializedName("status")
    val status: String? = null
    @SerializedName("totalResults")
    val totalResults: Int? = null
    @SerializedName("code")
    val code: String? = null
    @SerializedName("message")
    val message: String? = null
}