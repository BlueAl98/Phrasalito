package com.nayibit.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nayibit.database.room.dao.CategoryDao
import com.nayibit.database.room.dao.DeckDao
import com.nayibit.database.room.entities.CategoryEntity
import com.nayibit.database.room.entities.DeckEntity
import com.nayibit.database.room.entities.PhraseEntity


@Database(
    entities = [DeckEntity::class, CategoryEntity::class, PhraseEntity::class],
    version = 1,
    exportSchema = false
)
abstract  class AppDatabase : RoomDatabase() {
    abstract fun deckDao(): DeckDao
    abstract fun categoryDao(): CategoryDao

}