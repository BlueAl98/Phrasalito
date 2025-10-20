package com.nayibit.phrasalito_data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "phrases",
    foreignKeys = [
        ForeignKey(
            entity = DeckEntity::class,
            parentColumns = ["id"],
            childColumns = ["deckId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("deckId")]
)
data class PhraseEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val targetLanguage: String,
    val translation: String? = null,
    val deckId: Int,
    val isNotified : Int = 0,
    val example : String? = null,
)