package com.example.looppool

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.looppool.ActivityLogic.AddScore
import com.example.looppool.ActivityLogic.ClearScores
import com.example.looppool.ActivityLogic.GetScores
import com.example.looppool.ActivityLogic.Score.Score
import com.example.looppool.ActivityLogic.Words.Word
import com.example.looppool.ActivityLogic.Words.WordDatabase
import com.example.looppool.ActivityLogic.rememberSharedViewModel
import com.example.looppool.ActivityUI.GameActivity
import com.example.looppool.ActivityUI.Leaderboard
import com.example.looppool.ActivityUI.MainMenuActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val SharedViewModel = rememberSharedViewModel(this)

            val context: Context = this

            val database = WordDatabase.getInstance(this)
            CoroutineScope(Dispatchers.IO).launch {
                populateDatabase(context, database)
            }


            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "mainActivity", builder = {
                composable ("mainActivity"){ MainMenuActivity(navController, SharedViewModel) }

                composable("gameActivity") { GameActivity(navController, SharedViewModel) }

                composable("leaderboardActivity") { Leaderboard(navController, GetScores(context)) }
            })

        }
    }
}

suspend fun populateDatabase(context: Context,database: WordDatabase){
    val dao = database.dao()

    if(dao.getAllWords().isEmpty()){
        val inputStream = context.resources.openRawResource(R.raw.google_10000_english_no_swears)
        val words = inputStream.bufferedReader().readLines()

        words.forEach {
                word-> dao.upsertWord(Word(Word = word))
        }
    }
}