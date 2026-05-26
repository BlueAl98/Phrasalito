package com.nayibit.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nayibit.database.room.dao.CategoryDao
import com.nayibit.database.room.dao.DeckDao
import com.nayibit.database.room.dao.LanguageDao
import com.nayibit.database.room.dao.PhraseDao
import com.nayibit.database.room.entities.CategoryEntity
import com.nayibit.database.room.entities.DeckEntity
import com.nayibit.database.room.entities.LanguageEntity
import com.nayibit.database.room.entities.PhraseEntity

@Database(
    entities = [DeckEntity::class, CategoryEntity::class, PhraseEntity::class, LanguageEntity::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun deckDao(): DeckDao
    abstract fun categoryDao(): CategoryDao
    abstract fun phraseDao(): PhraseDao
    abstract fun languageDao(): LanguageDao
}