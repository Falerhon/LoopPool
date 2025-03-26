package com.example.looppool.ActivityUI

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.looppool.ActivityLogic.SharedViewModel
import com.example.looppool.R

@Composable
fun MainMenuActivity(navController : NavController, sharedViewModel: SharedViewModel) {
    val word = "Android"
    val definition = "An OS that is way better than IOS dwmaidpwa jdoapdj waopj odwapdj wopajdowapj opajdwop ajwopaj opajodjowapjdwaop joapdjwo apjd awfh uiawh wioahwpajodp ajwopjd opwaj ddowppajdowajd owpwjopj odja opwaj uiosnvsneioa naiowihjj fipoka njiscanj wikoaniaopjo pj poj ipjieanfip jiojfoipeajoap jipjip pa;w "

    var usernameP1 by remember { mutableStateOf(sharedViewModel.usernameP1.value) } // Local state

    LaunchedEffect(sharedViewModel.usernameP1.collectAsState().value) {
        usernameP1 = sharedViewModel.usernameP1.value // Update UI when ViewModel changes
    }

    var usernameP2 by remember { mutableStateOf(sharedViewModel.usernameP2.value) } // Local state

    LaunchedEffect(sharedViewModel.usernameP2.collectAsState().value) {
        usernameP2 = sharedViewModel.usernameP2.value// Update UI when ViewModel changes
    }

    Column(
        modifier = Modifier.padding(top = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Image(painter = painterResource(R.drawable.eevee), contentDescription = "Icon of the app",
            modifier = Modifier
                .size(150.dp)
                .padding(16.dp))

        Box(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(Color.Gray)
                .weight(3F)){
            Column(Modifier.fillMaxWidth()){
                Text(
                    text = "Word of the day : ",
                    modifier = Modifier.padding(8.dp),
                    fontSize = 24.sp
                )

                Text(
                    text = word,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontSize = 32.sp
                )

                Text(
                    text = definition,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxHeight()
                        .verticalScroll(rememberScrollState()),
                    fontSize = 24.sp
                )
            }
        }

        Button(onClick = {
            navController.navigate("gameActivity")
        },
            modifier = Modifier.padding(16.dp)
                .weight(1F)
                .scale(0.75F, 0.75F)
        )
        {
            Text(
                text = "Start Game",
                modifier = Modifier.padding(16.dp),
                fontSize = 24.sp
            )
        }

        Row(
            modifier = Modifier.weight(.5f).fillMaxWidth().padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            TextField(value = usernameP1,
                onValueChange = {
                    usernameP1 = it
                    sharedViewModel.setUsernameForPlayer(it, 1)
                },
                label = { Text("Player 1") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.weight(1f))

            TextField(value = usernameP2,
                onValueChange = {
                    usernameP2 = it
                    sharedViewModel.setUsernameForPlayer(it, 2)
                },
                label = { Text("Player 2") },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(40.dp).weight(.5f))

    }

}
