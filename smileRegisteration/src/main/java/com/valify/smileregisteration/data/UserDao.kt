package com.valify.smileregisteration.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    suspend fun getCurrentUser(): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUser(user: User)
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(user: User)
    @Delete
    suspend fun deleteCurrentUser(user: User)
}
