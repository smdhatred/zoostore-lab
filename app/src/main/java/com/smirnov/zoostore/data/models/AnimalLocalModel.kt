package com.smirnov.zoostore.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.smirnov.zoostore.domain.models.AnimalModel

@Entity(tableName = "animals")
data class AnimalLocalModel(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "price")
    val price: Float,
    @ColumnInfo(name = "image_url")
    val imageUrl: String,
    @ColumnInfo(name = "age")
    val age: Long,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "in_cart")
    val inCart: Boolean,
)

fun AnimalLocalModel.asDomainModel(): AnimalModel = AnimalModel(
    id = id,
    name = name,
    price = price,
    imageUrl = imageUrl,
    age = age,
    description = description,
    inCart = inCart,
)

fun AnimalModel.asLocalModel(): AnimalLocalModel = AnimalLocalModel(
    id = id,
    name = name,
    price = price,
    imageUrl = imageUrl,
    age = age,
    description = description,
    inCart = inCart,
)