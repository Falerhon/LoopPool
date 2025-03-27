package com.example.looppool

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.looppool.ActivityLogic.AddScore
import com.example.looppool.ActivityLogic.ClearScores
import com.example.looppool.ActivityLogic.GetScores
import com.example.looppool.ActivityLogic.Score.Score
import com.example.looppool.ActivityLogic.rememberSharedViewModel
import com.example.looppool.ActivityUI.GameActivity
import com.example.looppool.ActivityUI.Leaderboard
import com.example.looppool.ActivityUI.MainMenuActivity

private val ScoreSample = listOf(
    Score(0, "Simon", 10.0f),
    Score(0, "Sharon", 2.0f),
    Score(0, "Billy", 51.0f),
    Score(0, "Bob", 97.0f),
    Score(0, "Theo", 61.0f),
    Score(0, "Mem", 1.0f),
    Score(0, "Dan Heng", 21.0f),
    Score(0, "March", 91.0f),
    Score(0, "Himeko", 13.0f),
    Score(0, "Castorice", 1.0f),
    Score(0, "Silph", 85.0f),
    Score(0, "Midey", 80.0f),
    Score(0, "Clara", 200.0f),
    Score(0, "SVAROG", 199.0f),
    Score(0, "Feixiao", 340.0f),
    Score(0, "Silver Wolf", 999.0f),
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val SharedViewModel = rememberSharedViewModel(this)

            val context: Context = this

            //TODO : REMOVE THOSE AFTER SCORE IMPLEMENTATION
            ClearScores(this)
            AddScore(this, ScoreSample)

            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "mainActivity", builder = {
                composable ("mainActivity"){ MainMenuActivity(navController, SharedViewModel) }

                composable("gameActivity") { GameActivity(navController, SharedViewModel) }

                composable("leaderboardActivity") { Leaderboard(navController, GetScores(context)) }
            })

        }
    }
}