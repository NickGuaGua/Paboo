package com.guagua.paboo.data.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.guagua.data.bean.ArticleBean
import com.guagua.data.extension.parseUTCDate

@Entity(tableName = "articles", primaryKeys = ["title", "url"])
data class Article(
    val id: String,
    @Embedded val source: Source,
    @ColumnInfo(name = "author") val author: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "urlToImage") val urlToImage: String,
    @ColumnInfo(name = "publishedAt") val publishedAt: Long,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "country") val country: Country?,
    @ColumnInfo(name = "category") val category: Category?
) {
    companion object {
        internal fun create(
            bean: ArticleBean,
            country: Country?,
            category: Category?
        ) = with(bean) {
            Article(
                url ?: error("No url founded."),
                Source.create(source ?: error("No Source founded.")),
                author ?: "",
                title ?: error("No title founded."),
                description ?: "",
                url ?: error("No url founded."),
                urlToImage ?: "",
                publishedAt?.parseUTCDate() ?: error("No publish time."),
                content ?: "",
                country,
                category
            )
        }
    }
}