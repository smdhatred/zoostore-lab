package com.smirnov.zoostore.ui.animal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import com.smirnov.zoostore.core.Result
import com.smirnov.zoostore.domain.models.AnimalModel
import com.smirnov.zoostore.domain.repositories.AnimalsRepository
import javax.inject.Inject

@HiltViewModel
class AnimalViewModel @Inject constructor(
    private val animalsRepository: AnimalsRepository,
) : ViewModel() {

    private lateinit var args: AnimalActivityArguments

    private val mutableAnimal: MutableLiveData<AnimalModel> = MutableLiveData()
    val animal: LiveData<AnimalModel> = mutableAnimal

    fun initWithArgs(value: AnimalActivityArguments) {
        if (!::args.isInitialized) {
            args = value
        }
        viewModelScope.launch {
            loadAnimal()
        }
    }

    fun onCartClick() {
        val animal = animal.value ?: return
        viewModelScope.launch {
            val result = if (animal.inCart) {
                animalsRepository.removeAnimalFromCart(animal)
            } else {
                animalsRepository.addAnimalToCart(animal)
            }
            when (result) {
                is Result.Success -> {
                    loadAnimal()
                }

                is Result.Error -> {}
            }
        }
    }

    private suspend fun loadAnimal() {
        when (val result = animalsRepository.getAnimal(args.animalId)) {
            is Result.Success -> {
                val animal = result.value
                mutableAnimal.postValue(animal)
            }

            is Result.Error -> {}
        }
    }
}