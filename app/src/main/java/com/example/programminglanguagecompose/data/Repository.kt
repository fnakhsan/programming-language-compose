package com.example.programminglanguagecompose.data

import android.util.Log
import com.example.programminglanguagecompose.data.local.database.FavoriteDao
import com.example.programminglanguagecompose.data.model.Language
import com.example.programminglanguagecompose.data.model.LanguagesData
import com.example.programminglanguagecompose.utils.Tag
import com.example.programminglanguagecompose.utils.UiText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class Repository(private val mFavDao: FavoriteDao) {
    fun getLanguages(): Flow<Resource<List<Language>>> = flow {
        emit(Resource.Loading)
        try {
            val response = LanguagesData.listData
            Log.d(Tag.repository, response.toString())
            emit(Resource.Success(response))
        } catch (e: Exception) {
            Log.e(Tag.repository, Log.getStackTraceString(e))
            emit(Resource.Error(UiText.DynamicString(e.message ?: "Unknown Error")))
        }
    }.flowOn(Dispatchers.IO)

    fun searchLanguages(query: String): Flow<Resource<List<Language>>> = flow {
        emit(Resource.Loading)
        try {
            val response = LanguagesData.listData.filter {
                it.name.contains(query, ignoreCase = true)
            }
            Log.d(Tag.repository, response.toString())
            emit(Resource.Success(response))
        } catch (e: Exception) {
            Log.e(Tag.repository, Log.getStackTraceString(e))
            emit(Resource.Error(UiText.DynamicString(e.message ?: "Unknown Error")))
        }
    }.flowOn(Dispatchers.IO)

    fun getFavoriteLanguages(): Flow<Resource<Flow<List<Language>>>> = flow {
        emit(Resource.Loading)
        try {
            val response = mFavDao.getAllFav()
            Log.d(Tag.repository, response.toString())
            emit(Resource.Success(response))
        } catch (e: Exception) {
            Log.e(Tag.repository, Log.getStackTraceString(e))
            emit(Resource.Error(UiText.DynamicString(e.message ?: "Unknown Error")))
        }
    }.flowOn(Dispatchers.IO)

    fun searchFavoriteLanguages(query: String): Flow<Resource<Flow<List<Language>>>> = flow {
        emit(Resource.Loading)
        try {
            val response = mFavDao.searchFav("%$query%")
            Log.d(Tag.repository, response.toString())
            emit(Resource.Success(response))
        } catch (e: Exception) {
            Log.e(Tag.repository, Log.getStackTraceString(e))
            emit(Resource.Error(UiText.DynamicString(e.message ?: "Unknown Error")))
        }
    }.flowOn(Dispatchers.IO)

    fun getLanguageDetails(id: Int): Flow<Resource<Language>> = flow {
        emit(Resource.Loading)
        try {
            //lol idk how to create a proper filtering for this
            val response: Language = LanguagesData.listData.first {
                it.id == id
            }
            Log.d(Tag.repository, response.toString())
            emit(Resource.Success(response))
        } catch (e: Exception) {
            Log.e(Tag.repository, Log.getStackTraceString(e))
            when (e) {
                is NoSuchElementException -> {
                    emit(Resource.Error(UiText.DynamicString("Language details cannot be found")))
                }
                else -> {
                    emit(Resource.Error(UiText.DynamicString(e.message ?: "Unknown Error")))
                }
            }
        }
    }.flowOn(Dispatchers.IO)

    suspend fun addedToFavorite(language: Language) {
        mFavDao.insert(language)
    }

    suspend fun removeFromFavorite(language: Language) {
        mFavDao.delete(language)
    }

    fun isFavorite(id: Int): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading)
        try {
            //lol idk how to create a proper filtering for this
            val response = mFavDao.isFavorite(id)
            Log.d(Tag.repository, response.toString())
            emit(Resource.Success(response))
        } catch (e: Exception) {
            Log.e(Tag.repository, Log.getStackTraceString(e))
            emit(Resource.Error(UiText.DynamicString(e.message ?: "Unknown Error")))
        }
    }.flowOn(Dispatchers.IO)

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            favoriteDao: FavoriteDao
        ): Repository = instance ?: synchronized(this) {
            instance ?: Repository(favoriteDao)
        }.also { instance = it }
    }
}