package com.djw.mygithubuserapp.di

import android.content.Context
import com.djw.mygithubuserapp.database.UserRepository
import com.djw.mygithubuserapp.database.local.room.UserRoomDatabase
import com.djw.mygithubuserapp.database.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val apiService = ApiConfig.getApiService()
        val database = UserRoomDatabase.getDatabase(context)
        val dao = database.userDao()
        return UserRepository.getInstance(apiService, dao)
    }
}