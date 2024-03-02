package com.smirnov.zoostore.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import com.smirnov.zoostore.core.Result
import com.smirnov.zoostore.domain.models.AnimalModel
import com.smirnov.zoostore.domain.models.UserModel
import com.smirnov.zoostore.domain.repositories.AnimalsRepository
import com.smirnov.zoostore.domain.repositories.UserRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val animalsRepository: AnimalsRepository,
) : ViewModel() {

    private val mutableUser: MutableLiveData<UserModel> = MutableLiveData()
    val user: LiveData<UserModel> = mutableUser

    private val mutableAnimals: MutableLiveData<List<AnimalModel>> = MutableLiveData()
    val animals: LiveData<List<AnimalModel>> = mutableAnimals

    init {
        viewModelScope.launch {
            when (val userResult = userRepository.getUser()) {
                is Result.Success -> {
                    val user = userResult.value
                    mutableUser.postValue(user)
                }
                is Result.Error -> {}
            }

            when (val animalsResult = animalsRepository.getAllAnimals()) {
                is Result.Success -> {
                    val animals = animalsResult.value
                    mutableAnimals.postValue(animals)
                }
                is Result.Error -> {}
            }
        }
    }
}