package com.djw.mygithubuserapp.database

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.djw.mygithubuserapp.database.local.entity.User
import com.djw.mygithubuserapp.database.local.room.UserDao
import com.djw.mygithubuserapp.database.remote.retrofit.ApiService

class UserRepository private constructor(
    private val apiService: ApiService,
    private val userDao: UserDao
    ){

    fun getDetailUser(username: String): LiveData<Result<User>> = liveData {
        emit(Result.Loading)
        try {
            val users = apiService.getDetailUser(username)
            val isFavorite = userDao.isUserFavorite(users.login)
            // userDao.deleteAll()
            userDao.insertUser(User(
                users.login,
                users.avatarUrl,
                users.name,
                users.publicRepos,
                users.company,
                users.location,
                users.followers,
                users.following,
                isFavorite
            ))
        } catch (e: Exception) {
            Log.d("UserRepository", "getDetailUser: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
        val localData: LiveData<Result<User>> = userDao.getDetailUser(username).map { Result.Success(it) }
        emitSource(localData)
    }

    fun getFavorite(): LiveData<List<User>> {
        return userDao.getFavorite()
    }

    suspend fun setFavorite(user: User, favoriteState: Boolean) {
        user.isFavorite = favoriteState
        userDao.updateUser(user)
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            apiService: ApiService,
            userDao: UserDao
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, userDao)
            }.also { instance = it }
    }
}