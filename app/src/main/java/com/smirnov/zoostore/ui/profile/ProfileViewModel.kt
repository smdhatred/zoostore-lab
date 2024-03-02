package com.smirnov.zoostore.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import com.smirnov.zoostore.core.Result
import com.smirnov.zoostore.domain.models.UserModel
import com.smirnov.zoostore.domain.repositories.UserRepository
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val mutableUser: MutableLiveData<UserModel> = MutableLiveData()
    val user: LiveData<UserModel> = mutableUser

    init {
        viewModelScope.launch {
            when (val userResult = userRepository.getUser()) {
                is Result.Success -> {
                    val user = userResult.value
                    mutableUser.postValue(user)
                }

                is Result.Error -> {}
            }
        }
    }
}