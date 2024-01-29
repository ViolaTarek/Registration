package com.valify.smileregisteration.data

import android.media.Image
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    var userId: Int = 0,
    @ColumnInfo(name = "user_name")
    val userName: String,
    @ColumnInfo(name = "phone_number")
    val phone: String,
    @ColumnInfo(name = "email")
    val email: String,
    @ColumnInfo(name = "password")
    val password: String,
    @ColumnInfo(name = "image")
    val image: String?=null
)
