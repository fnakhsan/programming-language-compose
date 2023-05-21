package com.example.programminglanguagecompose.data.local.database

import androidx.room.*
import com.example.programminglanguagecompose.data.model.Language

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: Language)

    @Query("SELECT * FROM language")
    fun getAllFav(): List<Language>

    @Query("SELECT * FROM language WHERE language.name LIKE :name")
    fun searchFav(name: String): List<Language>

    @Query("SELECT * FROM language WHERE language.name = :name")
    fun getFav(name: String): Language

    @Query("SELECT EXISTS(SELECT * FROM language WHERE language.id = :id)")
    fun isFavorite(id: Int): Boolean

    @Delete
    suspend fun delete(favorite: Language)
}