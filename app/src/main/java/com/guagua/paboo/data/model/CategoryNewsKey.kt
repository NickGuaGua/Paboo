package com.guagua.paboo.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_news_key", primaryKeys = ["country", "category"])
data class CategoryNewsKey(
    @ColumnInfo(name = "country") val country: Country,
    @ColumnInfo(name = "category") val category: Category,
    @ColumnInfo(name = "page") val page: Int
)