package com.guagua.data.bean

import com.google.gson.annotations.SerializedName
import com.guagua.data.bean.base.ResponseBean

data class SourcesResponseBean(
    @SerializedName("sources") val sources: List<SourceDetailBean>
): ResponseBean()
