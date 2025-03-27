package com.example.looppool.ActivityLogic.Score

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface ScoreDao {

    @Upsert
    fun upsertScore(score: Score)

    @Delete
    fun deleteScore(score: Score)

    @Query("DELETE FROM scores")
    fun clearScores()

    @Query("SELECT * FROM scores ORDER BY score ASC")
    fun getScoresOrderedByScore() : List<Score>
}