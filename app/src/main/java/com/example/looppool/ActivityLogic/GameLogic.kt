package com.example.looppool.ActivityLogic;

import android.content.Context
import androidx.navigation.NavController
import com.example.looppool.ActivityLogic.Score.Score



class GameLogic(currContext: Context,navController : NavController, sharedViewModel: SharedViewModel) {

    val appContext : Context = currContext
    val nav = navController
    val u1 : String = sharedViewModel.usernameP1.value
    val u2 : String = sharedViewModel.usernameP2.value


    fun EndGame(score : Score) {
        AddScore(appContext, score)
        nav.navigate("leaderboardActivity")
    }
}