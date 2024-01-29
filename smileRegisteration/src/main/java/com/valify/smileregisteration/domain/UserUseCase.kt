package com.valify.smileregisteration.domain

import com.valify.smileregisteration.data.User
import com.valify.smileregisteration.data.UserRepository
import javax.inject.Inject


class UserUseCase @Inject constructor(private val userRepo: UserRepository){
    suspend fun getCurrentUser() = userRepo.getCurrentUser()
    suspend fun saveUser(user: User) = userRepo.saveUser(user)
    suspend fun updateUser(user: User)=userRepo.updateUser(user)

}