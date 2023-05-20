package com.example.programminglanguagecompose.di

import android.content.Context
import com.example.programminglanguagecompose.data.Repository
import com.example.programminglanguagecompose.data.local.database.FavoriteDatabase

object Injection {
    fun provideRepository(context: Context): Repository {
        val database = FavoriteDatabase.getDatabase(context)
        val dao = database.favoriteDao()
        return Repository.getInstance(dao)
    }
}