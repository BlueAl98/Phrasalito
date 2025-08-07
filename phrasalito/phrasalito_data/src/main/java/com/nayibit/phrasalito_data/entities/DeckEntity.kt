package com.nayibit.phrasalito_data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nayibit.phrasalito_domain.model.Deck

@Entity(tableName = "decks")
data class DeckEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val maxCards: Int
){
    fun toDomain(): Deck {
        return Deck(
            id = id,
            name = name,
            maxCards = maxCards)
    }
}
