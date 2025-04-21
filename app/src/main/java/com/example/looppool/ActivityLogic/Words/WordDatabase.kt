package com.example.looppool.ActivityLogic.Words

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.looppool.ActivityLogic.Words.API.DictionaryResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

@Database(entities = [Word::class], version = 2, exportSchema = false)
abstract class WordDatabase: RoomDatabase() {
    abstract fun dao(): WordDao

    //Singleton access
    companion object {
        @Volatile
        private var INSTANCE: WordDatabase? = null

        fun getInstance(context: Context): WordDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordDatabase::class.java,
                    "word_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    suspend fun fetchWordFromAPI(word: String, dao : WordDao): Word? = withContext(Dispatchers.IO) {
        try {
            val urlString = "https://api.dictionaryapi.dev/api/v2/entries/en/${word}"
            val url = URL(urlString);
            val connection = url.openConnection() as HttpURLConnection

            connection.requestMethod = "GET"
            connection.connectTimeout = 10000
            connection.readTimeout = 10000

            val responseCode = connection.responseCode
            Log.d("DictAPI", "Response code: $responseCode")
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val inputStream = connection.inputStream
                val json = inputStream.bufferedReader().use { it.readText() }

                val gson = Gson()
                val responseList: List<DictionaryResponse> =
                    gson.fromJson(json, Array<DictionaryResponse>::class.java).toList()

                val firstEntry = responseList.firstOrNull()
                val definition =
                    firstEntry?.meanings?.firstOrNull()?.definitions?.firstOrNull()?.Definition

                if (firstEntry != null) {
                    val wordEntry : Word
                    if(definition == null)
                        wordEntry = Word(Word = word.lowercase(), Description = "")
                    else
                        wordEntry = Word(Word = word.lowercase(), Description = definition)

                    dao.insertOrReplace(wordEntry)
                    return@withContext wordEntry
                }
            }
            null
        } catch (e: Exception) {
            Log.e("API", "Exception occurred", e)
            null
        }
    }
}