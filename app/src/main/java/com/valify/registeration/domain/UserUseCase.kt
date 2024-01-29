package com.valify.registeration.domain

import com.valify.registeration.data.User
import com.valify.registeration.data.UserRepository
import javax.inject.Inject


class UserUseCase @Inject constructor(private val userRepo: UserRepository){
    suspend fun getCurrentUser() = userRepo.getCurrentUser()
    suspend fun saveUser(user: User) = userRepo.saveUser(user)

}