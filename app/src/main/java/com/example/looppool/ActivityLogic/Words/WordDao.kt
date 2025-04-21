package com.example.looppool.ActivityLogic.Words

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface WordDao {

    @Upsert
    suspend fun upsertWord(word: Word)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(word: Word)

    @Delete
    suspend fun deleteWord(word: Word)

    @Query("DELETE FROM words")
    suspend fun clearWords()

    @Query("SELECT * FROM words WHERE word = :searchedWord")
    suspend fun getWord(searchedWord: String) : Word

    @Query("SELECT * FROM words")
    suspend fun getAllWords(): List<Word>

    @Query("SELECT * FROM words WHERE word LIKE '%' || :letter")
    suspend fun getAllWordsEndingWithLetter(letter : Char) : List<Word>

    @Query("SELECT * FROM words WHERE LOWER(word) LIKE LOWER(:letter || '%')")
    suspend fun getAllWordsStartingWithLetter(letter: String): List<Word>

    @Query("UPDATE words SET description = :newDescription WHERE word = :word")
    suspend fun updateDescription(word: String, newDescription: String)
}