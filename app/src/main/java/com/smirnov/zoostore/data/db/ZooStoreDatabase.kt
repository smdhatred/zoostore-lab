package com.smirnov.zoostore.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.smirnov.zoostore.data.dao.AnimalsDao
import com.smirnov.zoostore.data.models.AnimalLocalModel

@Database(
    entities = [AnimalLocalModel::class],
    version = 1,
    exportSchema = false,
)
abstract class ZooStoreDatabase: RoomDatabase() {

    abstract val animalsDao: AnimalsDao
}