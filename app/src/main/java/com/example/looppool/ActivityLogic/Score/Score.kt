package com.example.looppool.ActivityLogic.Score

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scores")
data class Score (
    @PrimaryKey(autoGenerate = true)
    val ScoreId: Int = 0,
    @ColumnInfo(name = "winnerName")
    val WinnerName: String,
    @ColumnInfo(name = "looserName")
    val LooserName: String,
    @ColumnInfo(name = "score")
    val Score: Float
)