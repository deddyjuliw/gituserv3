package com.djw.mygithubuserapp.ui

import androidx.lifecycle.ViewModel
import com.djw.mygithubuserapp.database.UserRepository

class FavoriteViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun getFavorite() = userRepository.getFavorite()

}