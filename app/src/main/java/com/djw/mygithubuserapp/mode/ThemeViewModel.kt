package com.djw.mygithubuserapp.mode

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ThemeViewModel(private val pref: ModePreferences) : ViewModel() {
    fun getThemeMode(): LiveData<Boolean> {
        return pref.getThemeMode().asLiveData()
    }

    fun saveThemeMode(isNightModeOn: Boolean) {
        viewModelScope.launch {
            pref.saveThemeMode(isNightModeOn)
        }
    }
}