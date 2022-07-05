package com.guagua.paboo.data.model

data class Response<T>(
    val status: String,
    val data: T,
    val totalResults: Int = 1,
    val code: String = "200",
    val message: String? = null
)