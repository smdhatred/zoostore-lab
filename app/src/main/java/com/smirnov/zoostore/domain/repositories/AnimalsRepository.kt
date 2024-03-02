package com.smirnov.zoostore.domain.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.smirnov.zoostore.core.BaseRepository
import com.smirnov.zoostore.core.Result
import com.smirnov.zoostore.data.dao.AnimalsDao
import com.smirnov.zoostore.data.models.AnimalLocalModel
import com.smirnov.zoostore.data.models.asDomainModel
import com.smirnov.zoostore.data.models.asLocalModel
import com.smirnov.zoostore.domain.models.AnimalModel
import javax.inject.Inject

class AnimalsRepository @Inject constructor(
    private val animalsDao: AnimalsDao,
) : BaseRepository() {

    suspend fun getAllAnimals(): Result<List<AnimalModel>> = withContext(Dispatchers.IO) {
        handleResult {
            animalsDao.selectAllAnimals().map(AnimalLocalModel::asDomainModel)
        }
    }

    suspend fun getAnimal(animalId: Long): Result<AnimalModel> = withContext(Dispatchers.IO) {
        handleResult {
            animalsDao.selectAnimalById(animalId = animalId).asDomainModel()
        }
    }

    suspend fun getCartAnimals(): Result<List<AnimalModel>> = withContext(Dispatchers.IO) {
        handleResult {
            animalsDao.selectAnimalsInCart().map(AnimalLocalModel::asDomainModel)
        }
    }

    suspend fun addAnimalToCart(animal: AnimalModel): Result<Unit> = withContext(Dispatchers.IO) {
        handleResult {
            if (!animal.inCart) {
                animalsDao.updateAnimal(
                    animal = animal.copy(inCart = true).asLocalModel()
                )
            }
        }
    }

    suspend fun removeAnimalFromCart(animal: AnimalModel): Result<Unit> = withContext(Dispatchers.IO) {
        handleResult {
            if (animal.inCart) {
                animalsDao.updateAnimal(
                    animal = animal.copy(inCart = false).asLocalModel()
                )
            }
        }
    }

    suspend fun buyAnimals(animals: List<AnimalModel>): Result<Unit> = withContext(Dispatchers.IO) {
        handleResult {
            val localAnimals = animals
                .map(AnimalModel::asLocalModel)
                .map { it.copy(inCart = false) }
            animalsDao.updateAnimals(localAnimals)
        }
    }
}