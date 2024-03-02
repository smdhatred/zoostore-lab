package com.smirnov.zoostore.domain.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.smirnov.zoostore.core.Result
import com.smirnov.zoostore.domain.models.UserModel

class UserRepository() {

    suspend fun getUser(): Result<UserModel> = withContext(Dispatchers.IO) {
        Result.Success(defaultUser)
    }
}

private val defaultUser = UserModel(
    firstName = "Дмитрий",
    lastName = "Смирнов",
    group = "МОАИСМ-О-22/1",
    photoUrl = "https://ih1.redbubble.net/image.4972345603.9093/st,small,507x507-pad,600x600,f8f8f8.jpg",
)