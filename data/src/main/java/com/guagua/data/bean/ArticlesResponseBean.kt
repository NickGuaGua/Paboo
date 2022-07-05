package com.guagua.data.bean

import com.google.gson.annotations.SerializedName
import com.guagua.data.bean.base.ResponseBean

data class ArticlesResponseBean(
    @SerializedName("articles") val articles: List<ArticleBean>?
): ResponseBean()
