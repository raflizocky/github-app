package com.zocky.githubapp.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zocky.githubapp.data.pref.SettingPreferences
import com.zocky.githubapp.data.room.repository.FavoriteUserRepository
import com.zocky.githubapp.ui.favorite.FavoriteViewModel
import com.zocky.githubapp.ui.theme.ThemeViewModel

class ViewModelFactory(private val application: Application, private val pref: SettingPreferences) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ThemeViewModel::class.java)) {
            return ThemeViewModel(pref) as T
        } else if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(FavoriteUserRepository(application)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
