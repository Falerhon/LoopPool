package com.example.looppool.ActivityLogic.Words

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Word::class], version = 1, exportSchema = false)
abstract class WordDatabase: RoomDatabase() {
    abstract fun dao(): WordDao

    fun w() {
        println("hello")
    }
}