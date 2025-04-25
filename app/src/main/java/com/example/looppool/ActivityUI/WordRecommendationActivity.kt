package com.example.looppool.ActivityUI

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.MutableState
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
import com.example.looppool.ActivityLogic.GameLogic
import com.example.looppool.ActivityLogic.GameManager
import com.example.looppool.ActivityLogic.SharedViewModel
import com.example.looppool.ActivityLogic.Words.Word
import com.example.looppool.ActivityLogic.Words.WordDao
import com.example.looppool.ActivityLogic.Words.WordDatabase
import kotlin.text.ifEmpty

@Composable
fun WordRecommendation(navController: NavController, sharedViewModel: SharedViewModel){
    val gameLogic = GameManager.getInstance(LocalContext.current, navController, sharedViewModel)
    val database = WordDatabase.getInstance(LocalContext.current)
    val lastWord = gameLogic.lastWords.first()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .padding(top = 40.dp)
                .padding(8.dp)
        ) {
            Text(
                text = "WORD RECOMMENDED",
                modifier = Modifier.padding(8.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp
            )
            Text(
                text = "You could have used those words to continue the chain",
                modifier = Modifier.padding(4.dp),
                fontSize = 16.sp
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ShowWordRecommendation(lastWord, database)
            }
        }

        Column(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .padding(8.dp)
                .align(Alignment.BottomCenter)
        ) {
            Text(
                text = "WORD USED",
                modifier = Modifier.padding(8.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp
            )
            Text(
                text = "All of the words used in this game",
                modifier = Modifier.padding(4.dp),
                fontSize = 16.sp
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ShowAllUsedWords(gameLogic, database)
            }

            Button(
                onClick = {
                    navController.navigate("leaderboardActivity")
                },
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
                    .scale(0.75F, 0.75F)
            ) {
                Text(
                    text = "Continue",
                    modifier = Modifier.padding(16.dp),
                    fontSize = 24.sp
                )
            }
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

    LaunchedEffect(updatedWords) {
        // This ensures recomposition whenever updatedWords changes
        Unit
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
                    .wrapContentHeight(),
                elevation = cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
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

@Composable
fun ShowAllUsedWords(gameLogic: GameLogic, database: WordDatabase){
    val wordDao = database.dao()
    var updatedWords by remember { mutableStateOf<List<Word>>(emptyList()) }

    // Load words and their descriptions
    LaunchedEffect(gameLogic.lastWords) {
        val updatedList = mutableListOf<Word>()
        for (word in gameLogic.lastWords) {
            val dbWord = wordDao.getWord(word)
            if(dbWord == null)
                return@LaunchedEffect

            if(dbWord.Description.isEmpty()){
                val updatedWord = getWordDescriptions(dbWord, database, wordDao)
                updatedWord?.let { updatedList.add(it) }
            }
            else
                updatedList.add(dbWord)

        }
        updatedWords = updatedList
    }

    LaunchedEffect(updatedWords) {
        // This ensures recomposition whenever updatedWords changes
        Unit
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp) // space between items
    ) {

        items(updatedWords) { word ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                elevation = cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
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
