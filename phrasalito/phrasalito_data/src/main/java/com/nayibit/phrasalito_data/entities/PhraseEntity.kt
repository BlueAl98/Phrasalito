package com.nayibit.phrasalito_data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nayibit.phrasalito_domain.model.Phrase

@Entity(tableName = "phrases")
data class PhraseEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val targetLanguage: String,
    val translation: String
){
    fun toDomain(): Phrase {
        return Phrase(
            id = id,
            targetLanguage = targetLanguage,
            translation = translation
        )
    }
}