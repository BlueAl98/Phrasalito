package com.nayibit.database.room.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "categories",
    foreignKeys = [
        ForeignKey(
            entity = LanguageEntity::class,
            parentColumns = ["id"],
            childColumns = ["languageId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("languageId")]
)
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val subtitle: String = "",
    val maxDecks: Int = 20,
    val uuid: Long = 0,
    val languageId: Int
)
