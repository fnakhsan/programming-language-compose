package com.example.programminglanguagecompose.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.programminglanguagecompose.data.Repository
import com.example.programminglanguagecompose.di.Injection
import com.example.programminglanguagecompose.ui.screen.detail.DetailViewModel
import com.example.programminglanguagecompose.ui.screen.favorite.FavoriteViewModel
import com.example.programminglanguagecompose.ui.screen.home.HomeViewModel

class ViewModelFactory private constructor(private val repository: Repository): ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> return HomeViewModel(repository) as T
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> return FavoriteViewModel(repository) as T
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> return DetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class: " + modelClass.name)
    }

    companion object{
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory = instance?: synchronized(this) {
            instance ?: ViewModelFactory(Injection.provideRepository(context))
        }.also { instance = it }
    }
}