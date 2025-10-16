package com.nayibit.phrasalito_data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "decks")
data class DeckEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val maxCards: Int,
    val lngCode: String,
    val languageName: String,
    val isNotified: Boolean = false
)


