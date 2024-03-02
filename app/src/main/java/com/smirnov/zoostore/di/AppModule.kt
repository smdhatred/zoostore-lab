package com.smirnov.zoostore.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.smirnov.zoostore.data.dao.AnimalsDao
import com.smirnov.zoostore.data.db.ZooStoreDatabase
import com.smirnov.zoostore.domain.repositories.UserRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideAnimalsStoreDatabase(
        @ApplicationContext context: Context,
    ): ZooStoreDatabase {
        return Room
            .databaseBuilder(
                context,
                ZooStoreDatabase::class.java,
                "zoostore.db",
            )
            .createFromAsset("zoostore.db")
            .build()
    }

    @Provides
    fun provideAnimalsDao(database: ZooStoreDatabase): AnimalsDao = database.animalsDao

    @Provides
    fun provideUserRepository(): UserRepository = UserRepository()
}