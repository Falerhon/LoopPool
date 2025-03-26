package com.example.looppool

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.looppool.ActivityLogic.SharedViewModel
import com.example.looppool.ActivityLogic.rememberSharedViewModel
import com.example.looppool.ActivityUI.GameActivity
import com.example.looppool.ActivityUI.MainMenuActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val SharedViewModel = rememberSharedViewModel(this)


            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "mainActivity", builder = {
                composable ("mainActivity"){ MainMenuActivity(navController, SharedViewModel) }

                composable("gameActivity") { GameActivity(navController, SharedViewModel) }
            })

        }
    }
}