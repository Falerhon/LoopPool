package com.example.looppool.ActivityUI

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.looppool.ActivityLogic.GameManager
import com.example.looppool.ActivityLogic.SharedViewModel
import com.example.looppool.ActivityLogic.Words.Word
import com.example.looppool.ActivityLogic.Words.WordDao
import com.example.looppool.ActivityLogic.Words.WordDatabase

@Composable
fun WordRecommendation(navController: NavController, sharedViewModel: SharedViewModel){
    val gameLogic = GameManager.getInstance(LocalContext.current, navController, sharedViewModel)
    val database = WordDatabase.getInstance(LocalContext.current)

    val lastWord = gameLogic.lastWords.first();

    Column(
        Modifier
            .fillMaxSize()
            .padding(top = 40.dp)
            .padding(16.dp)
    ) {

        Text(
            text = "WORD RECOMMENDED",
            modifier = Modifier.padding(16.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp
        )
        Text(
            text = "You could have used those words to continue the chain",
            modifier = Modifier.padding(16.dp),
            fontSize = 20.sp
        )

        Column(
            modifier = Modifier
                .weight(1f) // Take up available vertical space
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            ShowWordRecommendation(lastWord, database)
        }

        // Bottom fixed section: Button
        Button(
            onClick = {
                navController.navigate("leaderboardActivity")
            },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally) // optional
                .scale(0.75F, 0.75F)
        ) {
            Text(
                text = "Continue",
                modifier = Modifier.padding(16.dp),
                fontSize = 24.sp
            )
        }

        Column(modifier = Modifier.weight(.5f)) {

        }
    }
}

@Composable
fun ShowWordRecommendation(lastWord: String, database: WordDatabase) {
    val wordDao = database.dao()
    var recommendedWords by remember { mutableStateOf<List<Word>>(emptyList()) }
    var updatedWords by remember { mutableStateOf<List<Word>>(emptyList()) }

    // Load words when the composable is first composed or when `lastWord` changes
    LaunchedEffect(lastWord) {
        val firstLetter = lastWord.lastOrNull()?.lowercaseChar() ?: return@LaunchedEffect
        recommendedWords = wordDao.getAllWordsStartingWithLetter(firstLetter.toString())
    }

    LaunchedEffect(recommendedWords) {
        val updatedList = mutableListOf<Word>()

        recommendedWords
            .shuffled()
            .take(10)
            .filter { it.Description.isEmpty() }
            .forEach { word ->
                val updatedWord = getWordDescriptions(word, database, wordDao)
                updatedWord?.let { updatedList.add(it) }
            }

        updatedWords = updatedList
    }


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp) // space between items
    ) {
        val displayedWords = if (updatedWords.size < 5) updatedWords else updatedWords.take(5)

        items(displayedWords) { word ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(8.dp),
                elevation = cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = word.Word,
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = word.Description.ifEmpty { "No description yet." },
                        fontSize = 16.sp
                    )
                }
            }
        }
    }

}

suspend fun getWordDescriptions(word : Word, database : WordDatabase, dao : WordDao) : Word? {
    val apiWord = database.fetchWordFromAPI(word.Word, dao)
    return apiWord
}
