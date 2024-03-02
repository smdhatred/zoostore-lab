package com.smirnov.zoostore.domain.models

data class AnimalModel(
    val id: Long,
    val name: String,
    val price: Float,
    val imageUrl: String,
    val age: Long,
    val description: String,
    val inCart: Boolean,
)
