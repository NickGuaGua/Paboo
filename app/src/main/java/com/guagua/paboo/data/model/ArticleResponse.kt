package com.guagua.paboo.data.model

import com.guagua.data.bean.ArticlesResponseBean

data class ArticleResponse(
    val articles: List<Article>,
    val totalResults: Int
) {
    companion object {
        fun create(bean: ArticlesResponseBean, country: Country?, category: Category?) = with(bean) {
            ArticleResponse(
                articles.map { Article.create(it, country, category) },
                totalResults ?: 0
            )
        }
    }
}