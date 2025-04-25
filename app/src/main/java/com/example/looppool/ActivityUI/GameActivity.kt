package com.example.looppool.ActivityUI

import android.os.Build
import android.os.CountDownTimer
import android.os.VibrationEffect
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavController
import com.example.looppool.ActivityLogic.GameLogic
import com.example.looppool.ActivityLogic.GameManager
import com.example.looppool.ActivityLogic.Score.Score
import com.example.looppool.ActivityLogic.SharedViewModel
import com.example.looppool.ActivityLogic.Words.Word
import com.example.looppool.ActivityLogic.Words.WordDatabase
import android.os.Vibrator
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.looppool.ActivityLogic.Words.API.DictionaryResponse
import com.example.looppool.ActivityLogic.Words.WordDao
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GameActivity(navController: NavController, sharedViewModel: SharedViewModel) {
    var modifier = Modifier
    val context = LocalContext.current
    val gameLogic = GameManager.getInstance(context, navController, sharedViewModel)
    val database = WordDatabase.getInstance(LocalContext.current)
    var wordEntered by remember { mutableStateOf(TextFieldValue("")) }
    var showPopup by remember { mutableStateOf(false) }
    var isEndGame by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    var resetTimer by remember { mutableStateOf(0) }

    val vibrator = context.getSystemService(Vibrator::class.java)

    Column(
        modifier
            .fillMaxSize()
            .padding(top = 40.dp)
            .padding(16.dp)
    ) {
        Box(
            Modifier
                .align(Alignment.Start)
                .weight(.5f)
        ) {
            Text("Last words : ", fontSize = 24.sp)
            PrintLastWords(gameLogic.lastWords)
        }

        Column(
            modifier
                .weight(1.0f)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Time Left", modifier = modifier, color = Color.Black, fontSize = 24.sp)

            CountDownTimer(resetTrigger = resetTimer, onTimeOut = { CallEndGame(gameLogic) } )

            TextField(
                value = wordEntered,
                onValueChange = { wordEntered = it },
                label = { Text("Enter a word") }
            )
            Button(
                onClick = {
                    if(wordEntered.text.length <= 0)
                        return@Button

                    coroutineScope.launch {
                        val isValid = VerifyWord(wordEntered.text, gameLogic, database)
                        if (isValid) {
                            message =
                                "${wordEntered.text} is valid! \n Pass the phone to Player ${if (gameLogic.lastWords.size % 2 == 1) 2 else 1}"
                            showPopup = true

                            val timings: LongArray = longArrayOf(50)
                            val amplitudes: IntArray = intArrayOf(33)
                            val repeatIndex = -1 // Do not repeat.
                            vibrator.vibrate(
                                VibrationEffect.createWaveform(
                                    timings,
                                    amplitudes,
                                    repeatIndex
                                )
                            )
                        } else {
                            message =
                                "${wordEntered.text} is invalid! \n Player ${if (gameLogic.lastWords.size % 2 == 0) 2 else 1} wins!"
                            showPopup = true
                            isEndGame = true

                            val timings: LongArray = longArrayOf(70, 50, 90)
                            val amplitudes: IntArray = intArrayOf(33, 0, 50)
                            val repeatIndex = -1 // Do not repeat.
                            vibrator.vibrate(
                                VibrationEffect.createWaveform(
                                    timings,
                                    amplitudes,
                                    repeatIndex
                                )
                            )
                        }
                    }
                },
                modifier = Modifier
                    .padding(16.dp)
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

        Column(modifier = Modifier.weight(.5f)) {

        }
    }

    if (showPopup) {
        if (isEndGame) {
            PopUpBox(
                showPopup = showPopup,
                message = message,
                onConfirm = {
                    CallEndGame(gameLogic)
                }
            )
        } else {
            PopUpBox(
                showPopup = showPopup,
                message = message,
                onConfirm = {
                    showPopup = false
                    wordEntered = TextFieldValue("")
                }
            )
        }
        resetTimer++
    }
}


@Composable
fun PrintLastWords(lastWords: List<String>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        var count = 0
        if (lastWords.size > 5)
            count = 5
        else
            count = lastWords.size

        items(count) { index ->
            Text(
                text = lastWords[index],
                modifier = Modifier
                    .padding(4.dp),
                fontSize = 18.sp
            )
        }
    }
}

suspend fun VerifyWord(
    wordToSearch: String,
    gameLogic: GameLogic,
    database: WordDatabase
): Boolean {
    val wordDao = database.dao()
    val cleanedWord = wordToSearch.trim().lowercase()
    val foundWord = wordDao.getWord(cleanedWord)

    val isValidWord = if (gameLogic.lastWords.isEmpty()) {
        if(foundWord != null) true
        else{

            val apiWord = database.fetchWordFromAPI(cleanedWord, wordDao)
            Log.d("DictAPI", "Response code: ${apiWord?.Word}")
            apiWord != null
        }
    } else {
        if(gameLogic.lastWords.contains(cleanedWord))
        {
            return false
        }
        if(foundWord == null){
            val apiWord = database.fetchWordFromAPI(cleanedWord, wordDao)
            Log.d("DictAPI", "Response code: ${apiWord?.Word}")
            if(apiWord == null)
                return false
        }

        val previousWord = gameLogic.lastWords.firstOrNull() ?: return false
        val firstLetterMatches = previousWord.lastOrNull()
            ?.equals(wordToSearch.firstOrNull() ?: ' ', ignoreCase = true) == true
        val isOneLetterAway = isOneLetterDifferent(previousWord, wordToSearch)

        firstLetterMatches || isOneLetterAway
    }

    if (isValidWord) {
        gameLogic.lastWords.add(0, wordToSearch)
        return true
    }

    return false
}

fun isOneLetterDifferent(str1: String, str2: String): Boolean {
    var i = 0
    var count = 0

    if (str1.length != str2.length)
        return false

    while (i < str1.length) {
        if (str1[i] !== str2[i]) count++
        i++
    }

    if (count == 1)
        return true
    return false
}

@Composable
fun CountDownTimer(totalTime : Int = 30, resetTrigger: Int, onTimeOut : () -> Unit){
    var timeLeft by remember(resetTrigger) { mutableStateOf(totalTime) }

    LaunchedEffect(resetTrigger) {
        while(timeLeft > 0){
            delay(1000L)
            timeLeft--
        }
        onTimeOut()
    }

    Text(
        text = "$timeLeft",
        fontSize = 36.sp,
        fontWeight = FontWeight.Bold
    )
}

fun CallEndGame(gameLogic: GameLogic) {
    var victoriousUsername: String
    var defeatedUsername: String
    if ((gameLogic.lastWords.size + 1)% 2 == 1)
    {
        victoriousUsername = gameLogic.u2
        defeatedUsername = gameLogic.u1
    }
    else
    {
        victoriousUsername = gameLogic.u1
        defeatedUsername = gameLogic.u2
    }


    val score = Score(0, victoriousUsername, defeatedUsername, gameLogic.lastWords.size.toFloat())
    gameLogic.EndGame(score)
}

@Composable
fun PopUpBox(showPopup: Boolean, message: String, onConfirm: () -> Unit) {
    if (showPopup) {
        Popup(
            alignment = Alignment.Center,
            properties = PopupProperties(excludeFromSystemGesture = true),
            onDismissRequest = { onConfirm() }
        )
        {
            Box(
                Modifier
                    .width(200.dp)
                    .height(300.dp)
                    .background(Color.Gray)
                    .clip(RoundedCornerShape(4.dp)),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = message,
                        color = Color.White,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(8.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = onConfirm) {
                        Text("Confirm")
                    }
                }
            }
        }
    }

}
