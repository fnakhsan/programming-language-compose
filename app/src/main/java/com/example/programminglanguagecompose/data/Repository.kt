package com.example.programminglanguagecompose.data

import com.example.programminglanguagecompose.data.model.Language
import com.example.programminglanguagecompose.data.model.LanguagesData

class Repository {
    fun getLanguages(): List<Language> {
        return LanguagesData.listData
    }

    fun searchLanguages(query: String): List<Language>{
        return LanguagesData.listData.filter {
            it.name.contains(query, ignoreCase = true)
        }
    }
}