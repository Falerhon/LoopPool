package com.example.looppool

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.looppool.ui.theme.LoopPoolTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "mainActivity", builder = {
                composable ("mainActivity"){ Greeting(word = "Android",
                    definition = "An OS that is way better than IOS dwmaidpwa jdoapdj waopj odwapdj wopajdowapj opajdwop ajwopaj opajodjowapjdwaop joapdjwo apjd awfh uiawh wioahwpajodp ajwopjd opwaj ddowppajdowajd owpwjopj odja opwaj uiosnvsneioa naiowihjj fipoka njiscanj wikoaniaopjo pj poj ipjieanfip jiojfoipeajoap jipjip pa;w ",
                    modifier = Modifier.padding(), navController) }

                composable("gameActivity") { GameActivityLayout(listOf<String>("apple", "bottle", "pneumonoultramicroscopicsilicovolcanoconiosis", "house", "keyboard")) }
            })

        }
    }
}

@Composable
fun Greeting(word: String, definition: String, modifier: Modifier = Modifier, navController : NavController? = null) {
    Column(
        modifier = Modifier.padding(top = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Image(painter = painterResource(R.drawable.eevee), contentDescription = "Icon of the app",
            modifier = Modifier
                .size(250.dp)
                .padding(16.dp))
        Text(
            text = "LoopPool",
            modifier = Modifier.padding(8.dp),
            fontSize = 48.sp
        )

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
            navController?.navigate("gameActivity")
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

        Spacer(modifier = Modifier.height(40.dp))
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LoopPoolTheme {
        Greeting("Android", "An OS that is way better than IOS dwmaidpwa jdoapdj waopj odwapdj wopajdowapj opajdwop ajwopaj opajodjowapjdwaop joapdjwo apjd awfh uiawh wioahwpajodp ajwopjd opwaj ddowppajdowajd owpwjopj odja opwaj uiosnvsneioa naiowihjj fipoka njiscanj wikoaniaopjo pj poj ipjieanfip jiojfoipeajoap jipjip pa;w ")
    }
}