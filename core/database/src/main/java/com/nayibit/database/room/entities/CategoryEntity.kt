package com.nayibit.database.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val subtitle: String = "",
    val maxDecks: Int = 20,
    val uuid : Long = 0
)
