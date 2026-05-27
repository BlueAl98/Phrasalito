package com.nayibit.database.room.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "decks",
     foreignKeys = [
         ForeignKey(
         entity = CategoryEntity::class,
         parentColumns = ["id"],
         childColumns = ["idCategory"],
         onDelete = ForeignKey.CASCADE)],
    indices = [Index("idCategory")]
    )
data class DeckEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val idCategory: Int,
    val name: String,
    val maxCards: Int = 20,
    val lngCode: String = "",
    val languageName: String,
    val isNotified: Boolean = false
)

