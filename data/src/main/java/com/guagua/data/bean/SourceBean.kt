package com.guagua.data.bean

import com.google.gson.annotations.SerializedName

data class SourceBean(
    @SerializedName("id") val id: String?,
    @SerializedName("name") val name: String?
)