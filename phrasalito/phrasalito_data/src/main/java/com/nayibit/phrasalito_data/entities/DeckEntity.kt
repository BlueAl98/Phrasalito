package com.nayibit.phrasalito_data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nayibit.common.util.Constants.MAX_NUMBER_OF_CARDS_PER_DECK

@Entity(tableName = "decks")
data class DeckEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val maxCards: Int = MAX_NUMBER_OF_CARDS_PER_DECK,
    val lngCode: String,
    val languageName: String,
    val isNotified: Boolean = false
)


