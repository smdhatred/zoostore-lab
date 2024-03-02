package com.smirnov.zoostore.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import com.smirnov.zoostore.core.Result
import com.smirnov.zoostore.core.SideEffect
import com.smirnov.zoostore.domain.models.AnimalModel
import com.smirnov.zoostore.domain.repositories.AnimalsRepository
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val animalsRepository: AnimalsRepository,
) : ViewModel() {

    private val mutableAnimals: MutableLiveData<List<AnimalModel>> = MutableLiveData()
    val animals: LiveData<List<AnimalModel>> = mutableAnimals

    private val mutableBuySuccessEffect: MutableLiveData<SideEffect<Unit>> = MutableLiveData()
    val buySuccessEffect: LiveData<SideEffect<Unit>> = mutableBuySuccessEffect

    init {
        loadAnimals()
    }

    fun removeFromCart(game: AnimalModel) {
        viewModelScope.launch {
            when (animalsRepository.removeAnimalFromCart(game)) {
                is Result.Success -> {
                    loadAnimals()
                }

                is Result.Error -> {}
            }
        }
    }

    fun loadAnimals() {
        viewModelScope.launch {
            when (val result = animalsRepository.getCartAnimals()) {
                is Result.Success -> {
                    val animals = result.value
                    mutableAnimals.postValue(animals)
                }

                is Result.Error -> {}
            }
        }
    }

    fun buyAnimals() {
        viewModelScope.launch {
            val animals = animals.value ?: return@launch
            when (animalsRepository.buyAnimals(animals)) {
                is Result.Success -> {
                    mutableBuySuccessEffect.postValue(SideEffect(Unit))
                    loadAnimals()
                }

                is Result.Error -> {}
            }
        }
    }
}