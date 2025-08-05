package com.nayibit.phrasalito_data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nayibit.phrasalito_data.dao.PhraseDao
import com.nayibit.phrasalito_data.entities.PhraseEntity

@Database(
    entities = [PhraseEntity::class],
    version = 1,
    exportSchema = false
)
abstract  class AppDatabase : RoomDatabase() {
    abstract fun phraseDao(): PhraseDao

}