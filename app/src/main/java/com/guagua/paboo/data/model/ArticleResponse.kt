package com.guagua.paboo.data.model

import com.guagua.data.bean.ArticlesResponseBean

data class ArticleResponse(
    val articles: List<Article>,
    val totalResults: Int,
    val status: Status,
    val errorCode: String,
    val errorMessage: String
) {
    fun isError() = status == Status.ERROR

    companion object {
        fun create(bean: ArticlesResponseBean, country: Country?, category: Category?) = with(bean) {
            ArticleResponse(
                articles?.map { Article.create(it, country, category) } ?: emptyList(),
                totalResults ?: 0,
                Status.from(status),
                code ?: "",
                message ?: ""
            )
        }
    }
}