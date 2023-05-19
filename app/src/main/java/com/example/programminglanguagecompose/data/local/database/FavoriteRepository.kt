package com.example.programminglanguagecompose.data.local.database

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.programminglanguagecompose.data.model.Language
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val mFavDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteDatabase.getDatabase(application)
        mFavDao = db.favoriteDao()
    }

    fun insert(Language: Language) {
        executorService.execute {
            mFavDao.insert(Language)
        }
    }

    fun getAllChanges(): LiveData<List<Language>> = mFavDao.getAllChanges()

    suspend fun getAll(): List<Language> = mFavDao.getAll()

    fun countFav(name: String): Int = mFavDao.countFav(name)

    fun update(Language: Language) {
        executorService.execute {
            mFavDao.update(Language)
        }
    }

    fun delete(Language: Language) {
        executorService.execute {
            mFavDao.delete(Language)
        }
    }
}