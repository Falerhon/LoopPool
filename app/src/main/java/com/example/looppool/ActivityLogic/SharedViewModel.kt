package com.example.looppool.ActivityLogic

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

object UserPreferences{
    private val Context.dataStore by preferencesDataStore("user_prefs")
    private val USERNAME_PLAYER_ONE_KEY = stringPreferencesKey("username_p1")
    private val USERNAME_PLAYER_TWO_KEY = stringPreferencesKey("username_p2")

    suspend fun saveUsername(context: Context, username : String, playerID: Int){
       if(playerID == 1){
           context.dataStore.edit{
                   prefs -> prefs[USERNAME_PLAYER_ONE_KEY] = username
           }
       }
        else{
           context.dataStore.edit{
                   prefs -> prefs[USERNAME_PLAYER_TWO_KEY] = username
           }
       }
    }

    fun getUsername(context: Context, playerID : Int) : Flow<String>{
        return context.dataStore.data.map { prefs ->
            when (playerID) {
                1 -> prefs[USERNAME_PLAYER_ONE_KEY] ?: "Player 1" // Default for Player 1
                2 -> prefs[USERNAME_PLAYER_TWO_KEY] ?: "Player 2" // Default for Player 2
                else -> ""
            }
        }
    }

}

class SharedViewModel(private val context : Context) : ViewModel(){
    private val _usernameP1 = MutableStateFlow("")
    val usernameP1: StateFlow<String> = _usernameP1.asStateFlow()

    private val _usernameP2 = MutableStateFlow("")
    val usernameP2: StateFlow<String> = _usernameP2.asStateFlow()

    init{
        viewModelScope.launch {
            UserPreferences.getUsername(context, 1).collect {
                _usernameP1.value = it
            }

            UserPreferences.getUsername(context, 2).collect {
                _usernameP2.value = it
            }
        }
    }

    fun setUsernameForPlayer(newUsername : String, playerID: Int){
        viewModelScope.launch {
            UserPreferences.saveUsername(context, newUsername, playerID)
            if(playerID == 1)
                _usernameP1.value = newUsername
            else
                _usernameP2.value = newUsername
        }
    }
}

@Composable
fun rememberSharedViewModel(context: Context): SharedViewModel {
    return remember { SharedViewModel(context) }
}