package com.valify.registeration.data

import com.valify.registeration.data.User
import com.valify.registeration.data.UserDao
import javax.inject.Inject

class UserRepository @Inject constructor (private val dao: UserDao) {


    suspend fun getCurrentUser(): User {
        return dao.getCurrentUser()
    }

    suspend fun saveUser(user: User) {
        return dao.saveUser(user)
    }

    suspend fun deleteUser(user: User) {
        return dao.deleteCurrentUser(user)
    }
}