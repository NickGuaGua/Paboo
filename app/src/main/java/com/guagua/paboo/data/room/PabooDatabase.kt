package com.guagua.paboo.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.guagua.paboo.data.model.Article
import com.guagua.paboo.data.model.CategoryNewsKey
import com.guagua.paboo.data.model.Source

@Database(entities = [Article::class, CategoryNewsKey::class], version = 1)
abstract class PabooDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
    abstract fun categoryKeyDao(): CategoryNewsKeyDao
}