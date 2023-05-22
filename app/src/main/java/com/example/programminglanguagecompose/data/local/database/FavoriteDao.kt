package com.example.programminglanguagecompose.data.local.database

import androidx.room.*
import com.example.programminglanguagecompose.data.model.Language
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: Language)

    @Query("SELECT * FROM language")
    fun getAllFav(): Flow<List<Language>>

    @Query("SELECT * FROM language WHERE language.name LIKE :name")
    fun searchFav(name: String): Flow<List<Language>>

    @Query("SELECT * FROM language WHERE language.name = :name")
    fun getFav(name: String): Language

    @Query("SELECT EXISTS(SELECT * FROM language WHERE language.id = :id)")
    fun isFavorite(id: Int): Boolean

    @Delete
    suspend fun delete(favorite: Language)
}