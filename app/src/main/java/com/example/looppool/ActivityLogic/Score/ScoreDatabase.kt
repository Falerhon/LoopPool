package com.example.looppool.ActivityLogic.Score

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Score::class], version = 1, exportSchema = false)
abstract class ScoreDatabase: RoomDatabase() {
    abstract fun dao(): ScoreDao

    fun w() {
        println("hello")
    }
}