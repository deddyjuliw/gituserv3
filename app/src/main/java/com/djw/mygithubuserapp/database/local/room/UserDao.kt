package com.djw.mygithubuserapp.database.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.djw.mygithubuserapp.database.local.entity.User

@Dao
interface UserDao {
    @Query("SELECT * FROM gthUser where username = :username")
    fun getDetailUser(username: String): LiveData<User>

    @Query("SELECT * FROM gthUser where favorite = 1")
    fun getFavorite(): LiveData<List<User>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(users: User)

    @Update
    suspend fun updateUser(user: User)

   // @Query("DELETE FROM gthUser WHERE favorite = 0")
    // suspend fun deleteAll()

    @Query("SELECT EXISTS(SELECT * FROM gthUser WHERE username = :username AND favorite = 1)")
    suspend fun isUserFavorite(username: String?): Boolean
}