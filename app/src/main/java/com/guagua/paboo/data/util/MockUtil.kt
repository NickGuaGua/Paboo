package com.guagua.paboo.data.util

import com.guagua.paboo.data.model.Article
import com.guagua.paboo.data.model.Category
import com.guagua.paboo.data.model.Country
import com.guagua.paboo.data.model.Source

object MockUtil {
    fun getArticles(
        total: Int = 10, country: Country = Country.TW, category: Category = Category.GENERAL
    ): List<Article> {
        return (1..total).map {
            Article(
                id = "id$it",
                source = Source("sourceId$it", "source$it"),
                author = "author$it",
                title = "title $it",
                description = "description $it",
                url = "https://www.google.com?q=$it",
                urlToImage = "https://picsum.photos/200",
                publishedAt = Long.MAX_VALUE,
                content = "content $it",
                country = country,
                category = category
            )
        }
    }
}