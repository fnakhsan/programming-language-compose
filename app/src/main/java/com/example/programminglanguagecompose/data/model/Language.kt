package com.example.programminglanguagecompose.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Language(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "name")
    var name: String = "",

    @ColumnInfo(name = "paradigm")
    var paradigm: String = "",

    @ColumnInfo(name = "developer")
    var developer: String = "",

    @ColumnInfo(name = "detail")
    var detail: String = "",

    @ColumnInfo(name = "photo")
    var photo: Int = 0
) : Parcelable