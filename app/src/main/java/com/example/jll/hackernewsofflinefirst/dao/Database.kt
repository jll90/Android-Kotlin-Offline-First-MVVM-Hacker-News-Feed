package com.example.jll.hackernewsofflinefirst.dao

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.jll.hackernewsofflinefirst.models.Article

@Database(entities = [Article::class], version = 1)
abstract class Database : RoomDatabase() {
  abstract fun articlesDao(): ArticlesDao
}