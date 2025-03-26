package com.example.looppool

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.looppool.ui.theme.LoopPoolTheme
import kotlin.collections.listOf
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

class GameActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState : Bundle?){
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val list = listOf<String>("apple", "bottle", "pneumonoultramicroscopicsilicovolcanoconiosis", "house", "keyboard")

        setContent{
            LoopPoolTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    innerPadding -> GameActivityLayout(
                        lastWords = list,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun GameActivityLayout(lastWords: List<String>, modifier: Modifier = Modifier){
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

            Button(onClick = {
                /*TODO*/
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

@Preview(showBackground = true)
@Composable
fun GameActivityLayoutPreview() {
    LoopPoolTheme {
        GameActivityLayout(listOf<String>("apple", "bottle", "cat", "house", "keyboard"))
    }
}