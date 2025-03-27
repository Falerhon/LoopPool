package com.example.looppool.ActivityUI

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.looppool.ActivityLogic.Score.Score
import com.example.looppool.ui.theme.LoopPoolTheme

@Composable
fun Leaderboard(navController : NavController, scores : List<Score>, modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .padding(top = 40.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally


    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(Color.Blue))
        {
            Text(text = "SCORE : ", color = Color.White,
                fontSize = 24.sp,
                modifier = Modifier.align(Alignment.Center))

        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(Color.Gray))
        {
            Box(modifier = Modifier.align(Alignment.CenterVertically)){
                Text(text = "Name", color = Color.White,
                    fontSize = 28.sp)
            }
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .weight(1.0f))
            Box(modifier = Modifier
                .wrapContentWidth(Alignment.End)
                .align(Alignment.CenterVertically)){
                Text(text = "Score", color = Color.White,
                    fontSize = 28.sp)
            }

        }
        LazyColumn (modifier = Modifier.fillMaxWidth()
            .weight(4.0f)){
            items(scores) { score ->
                ScoreRow(score)
            }
        }
        Button(onClick = {
            navController.navigate("mainActivity")
        },
            modifier = Modifier.padding(16.dp)
                .scale(0.75F, 0.75F)
                .weight(1.0f)
        )
        {
            Text(
                text = "Main Menu",
                modifier = Modifier.padding(16.dp),
                fontSize = 24.sp
            )
        }

    }

}

@Composable
fun ScoreRow(
    score : Score
) {
    Box(modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth()) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .border(1.dp, Color.Black)
        ) {
            Box(){
                Text(score.Name,
                    fontSize = 28.sp)
            }
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .weight(1.0f))
            Box(modifier = Modifier
                .wrapContentWidth(Alignment.End)
                .align(Alignment.CenterVertically)){
                Text(score.Score.toString(),
                    fontSize = 24.sp)
            }

        }
    }
}
private val ScorePreview = listOf(
    Score(0, "a", 10.0f),
    Score(0, "AA", 1.0f),
    Score(0, "b", 2.0f),
    Score(0, "c", 51.0f),
    Score(0, "d", 97.0f),
    Score(0, "e", 61.0f),
    Score(0, "f", 1.0f),
    Score(0, "g", 21.0f),
    Score(0, "h", 91.0f),
    Score(0, "i", 13.0f),
    Score(0, "j", 1.0f),
    Score(0, "k", 85.0f),
    Score(0, "l", 80.0f),
    Score(0, "m", 200.0f),
    Score(0, "n", 199.0f),
    Score(0, "o", 340.0f),
    Score(0, "p", 999.0f),
)

@Preview(showBackground = true)
@Composable
fun LeaderboardPreview() {
    LoopPoolTheme {


        val navController = rememberNavController()

        Leaderboard(navController, ScorePreview)
    }
}