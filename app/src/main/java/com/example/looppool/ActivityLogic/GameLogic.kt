package com.example.looppool.ActivityLogic;

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.navigation.NavController
import androidx.room.Room
import com.example.looppool.ActivityLogic.Score.Score
import com.example.looppool.ActivityLogic.Words.Word
import com.example.looppool.ActivityLogic.Words.WordDatabase
import com.example.looppool.R


class GameLogic(currContext: Context,navController : NavController, sharedViewModel: SharedViewModel) {

    val appContext : Context = currContext
    val nav = navController
    val u1 : String = sharedViewModel.usernameP1.value
    val u2 : String = sharedViewModel.usernameP2.value
    var lastWords = mutableStateListOf<String>()

       fun EndGame(score : Score) {
        AddScore(appContext, score)
           if(score.Score == 0.0f)
               nav.navigate("leaderboardActivity")
           else
            nav.navigate("wordRecommendationActivity")
       }
}

object GameManager{
    private var instance: GameLogic? = null

    fun getInstance(
        currContext: Context,
        navController: NavController,
        sharedViewModel: SharedViewModel
    ): GameLogic {
        if (instance == null) {
            instance = GameLogic(currContext, navController, sharedViewModel)
        }
        return instance!!
    }

    fun reset() {
        instance = null // Call this when you want a fresh game (e.g., on main menu)
    }
}