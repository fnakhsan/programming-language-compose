package com.example.programminglanguagecompose.data.local.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.programminglanguagecompose.data.model.Language

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favorite: Language)

    @Query("SELECT * FROM language")
    fun getAllChanges(): LiveData<List<Language>>

    @Query("SELECT * FROM language")
    suspend fun getAll(): List<Language>

    @Query("SELECT COUNT(*) FROM language where language.name = :name")
    fun countFav(name: String): Int

    @Update
    fun update(favorite: Language)

    @Delete
    fun delete(favorite: Language)
}