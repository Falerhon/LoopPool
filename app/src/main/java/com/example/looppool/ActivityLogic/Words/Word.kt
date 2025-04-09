package com.example.looppool.ActivityLogic.Words

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words")
data class Word (
    @PrimaryKey(autoGenerate = true)
    val WordId: Int = 0,
    @ColumnInfo(name = "word")
    var Word: String,
    @ColumnInfo(name = "description")
    var Description: String = ""
)