package com.nayibit.database.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "languages")
data class LanguageEntity(
    @PrimaryKey
    val id: Int,
    val code: String,
    val name: String,
    val flag: String,
    val status: Boolean,
    val isDownload: Boolean = false
)
