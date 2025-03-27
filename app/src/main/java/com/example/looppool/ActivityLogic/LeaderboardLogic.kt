package com.example.looppool.ActivityLogic

import android.content.Context
import androidx.room.Room
import com.example.looppool.ActivityLogic.Score.Score
import com.example.looppool.ActivityLogic.Score.ScoreDatabase

fun GetScores(context: Context) : List<Score> {

    val db : ScoreDatabase by lazy {
        Room.databaseBuilder(
            context,
            ScoreDatabase::class.java,
            "scores"
        ).allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    val scoreList : List<Score> = db.dao().getScoresOrderedByScore()

    db.close()

    return scoreList
}

fun ClearScores(context: Context) {

    val db : ScoreDatabase by lazy {
        Room.databaseBuilder(
            context,
            ScoreDatabase::class.java,
            "scores"
        ).allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    db.dao().clearScores()

    db.close()
}

fun AddScore(context: Context, score: Score) {

    val db : ScoreDatabase by lazy {
        Room.databaseBuilder(
            context,
            ScoreDatabase::class.java,
            "scores"
        ).allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    db.dao().upsertScore(score)

    db.close()
}

fun AddScore(context: Context, score: List<Score>) {

    val db : ScoreDatabase by lazy {
        Room.databaseBuilder(
            context,
            ScoreDatabase::class.java,
            "scores"
        ).allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    for (data in score)
    {
        db.dao().upsertScore(data)
    }

    db.close()
}