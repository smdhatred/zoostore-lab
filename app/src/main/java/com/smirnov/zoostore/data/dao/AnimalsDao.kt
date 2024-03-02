package com.smirnov.zoostore.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.smirnov.zoostore.data.models.AnimalLocalModel

@Dao
interface AnimalsDao {

    @Query("SELECT * FROM animals")
    suspend fun selectAllAnimals(): List<AnimalLocalModel>

    @Query("SELECT * FROM animals WHERE in_cart = 1")
    suspend fun selectAnimalsInCart(): List<AnimalLocalModel>

    @Query("SELECT * FROM animals WHERE id = :animalId")
    suspend fun selectAnimalById(animalId: Long): AnimalLocalModel

    @Update
    suspend fun updateAnimal(animal: AnimalLocalModel)

    @Update
    suspend fun updateAnimals(animals: List<AnimalLocalModel>)

    @Insert
    suspend fun insertAnimals(animal: List<AnimalLocalModel>)
}