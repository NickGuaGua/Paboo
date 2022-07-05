package com.guagua.paboo.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.guagua.paboo.data.model.Category
import com.guagua.paboo.data.model.CategoryNewsKey
import com.guagua.paboo.data.model.Country

@Dao
interface CategoryNewsKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(key: CategoryNewsKey)

    @Query("SELECT * FROM category_news_key WHERE country = :country AND category = :category")
    suspend fun queryKey(country: Country, category: Category): CategoryNewsKey?

    @Query("DELETE FROM category_news_key WHERE country = :country AND category = :category")
    suspend fun deleteKeys(country: Country, category: Category)
}