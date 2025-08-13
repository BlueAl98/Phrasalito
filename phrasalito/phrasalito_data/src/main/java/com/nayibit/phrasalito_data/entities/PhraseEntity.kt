package com.nayibit.phrasalito_data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.nayibit.phrasalito_domain.model.Phrase

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
    val translation: String,
    val deckId: Int
){
    fun toDomain(): Phrase {
        return Phrase(
            id = id,
            targetLanguage = targetLanguage,
            translation = translation
        )
    }
}