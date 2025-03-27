package com.example.looppool.ActivityUI

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.collections.listOf
import androidx.compose.material3.Button
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.looppool.ActivityLogic.GameLogic
import com.example.looppool.ActivityLogic.Score.Score
import com.example.looppool.ActivityLogic.SharedViewModel

@Composable
fun GameActivity(navController : NavController, sharedViewModel: SharedViewModel){
    var lastWords = listOf<String>("apple", "bottle", "pneumonoultramicroscopicsilicovolcanoconiosis", "house", "keyboard")
    var modifier = Modifier
    val context = LocalContext.current
    val gameLogic : GameLogic = GameLogic(context, navController, sharedViewModel)

    Column(modifier.fillMaxSize().padding(top = 40.dp).padding(16.dp)){
        Box(
            Modifier.align(Alignment.Start)
                .weight(.5f)
        ){
            Text("Last words : ", fontSize = 24.sp)
            PrintLastWords(lastWords)
        }

        Column(modifier.weight(1.0f).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center){
            Text(text = "Time Left", modifier = modifier, color=Color.Black, fontSize = 24.sp)

            Text(text = "30:00",modifier = modifier, fontSize = 36.sp, fontWeight = FontWeight.Bold)

            var wordEntered by remember { mutableStateOf(TextFieldValue("")) }
            TextField(
                value = wordEntered,
                onValueChange = { wordEntered = it },
                label = { Text("Enter a word") }
            )
            //TODO : SET THE RIGHT ON CLICK
            Button(onClick = {
                //TODO : Most Likely temp thing, remove
                val score = Score(0, gameLogic.u2, 10.0f)
                gameLogic.EndGame(score)
            },
                modifier = Modifier.padding(16.dp)
                    .scale(0.75F, 0.75F)
            )
            {
                Text(
                    text = "Send word",
                    modifier = Modifier.padding(16.dp),
                    fontSize = 24.sp
                )
            }
        }

        Column(modifier = Modifier.weight(.5f)){

        }
    }
}

@Composable
fun PrintLastWords(lastWords: List<String>, modifier: Modifier = Modifier){
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        items(lastWords.size) { index ->
            Text(
                text = lastWords[index],
                modifier = Modifier
                    .padding(4.dp),
                fontSize = 18.sp
            )
        }
    }
}
