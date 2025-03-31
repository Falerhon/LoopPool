package com.example.looppool.ActivityLogic.Words

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface WordDao {

    @Upsert
    suspend fun upsertWord(word: Word)

    @Delete
    suspend fun deleteWord(word: Word)

    @Query("DELETE FROM words")
    suspend fun clearWords()

    @Query("SELECT * FROM words WHERE word = :searchedWord")
    suspend fun getWord(searchedWord: String) : Word

    @Query("SELECT * FROM words")
    suspend fun getAllWords(): List<Word>
}