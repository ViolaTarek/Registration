package com.valify.registeration.data

import android.media.Image
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    var userId: Int = 0,
    val userName: String,
    val phone: String,
    val email: String,
    val password: String,
    val image: String?=null
)
