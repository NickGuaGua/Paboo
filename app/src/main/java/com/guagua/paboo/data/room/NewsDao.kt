package com.guagua.paboo.data.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.guagua.paboo.data.model.Article
import com.guagua.paboo.data.model.Category
import com.guagua.paboo.data.model.Country

@Dao
interface NewsDao {

    @Query("SELECT * FROM articles WHERE country = :country AND category = :category")
    fun queryArticles(country: Country, category: Category): PagingSource<Int, Article>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<Article>)

    @Query("DELETE FROM articles WHERE country = :country AND category = :category")
    suspend fun deleteArticles(country: Country, category: Category)
}