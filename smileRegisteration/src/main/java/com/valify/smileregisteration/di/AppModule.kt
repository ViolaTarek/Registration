package com.valify.smileregisteration.di

import android.content.Context
import androidx.room.Room
import com.valify.smileregisteration.data.AppDatabase
import com.valify.smileregisteration.data.UserDao
import com.valify.smileregisteration.data.UserRepository
import com.valify.smileregisteration.domain.UserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "room_database"
        ).build()
    }


    @Provides
    @Singleton
    fun provideDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao()
    }

    @Provides
    @Singleton
    fun provideUserRepository(userDao: UserDao): UserRepository {
        return UserRepository(userDao)
    }

    @Provides
    @Singleton
    fun provideHomeUseCase(userRepo: UserRepository) =
        UserUseCase(userRepo)

}
