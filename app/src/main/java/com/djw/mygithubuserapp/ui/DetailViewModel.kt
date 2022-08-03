package com.djw.mygithubuserapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.djw.mygithubuserapp.database.UserRepository
import com.djw.mygithubuserapp.database.local.entity.User
import kotlinx.coroutines.launch

class DetailViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun getDetailUser(username: String) = userRepository.getDetailUser(username)

    // Add favorite features in Detail Activity

    fun saveFavorite(user: User) {
        viewModelScope.launch {
            userRepository.setFavorite(user, true)
        }
    }

    fun deleteFavorite(user: User) {
        viewModelScope.launch {
            userRepository.setFavorite(user, false)
        }
    }
}