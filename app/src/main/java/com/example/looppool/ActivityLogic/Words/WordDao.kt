package com.example.looppool.ActivityLogic.Words

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface WordDao {

    @Upsert
    fun upsertWord(word: Word)

    @Delete
    fun deleteWord(word: Word)

    @Query("DELETE FROM words")
    fun clearWords()

    @Query("SELECT * FROM words WHERE word = :searchedWord")
    fun getWord(searchedWord: String) : List<Word>
}