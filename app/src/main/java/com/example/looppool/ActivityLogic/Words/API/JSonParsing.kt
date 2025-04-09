package com.example.looppool.ActivityLogic.Words.API

data class DictionaryResponse(
    val word : String,
    val meanings: List<Meaning>
)

data class Meaning(
    val partOfSpeach: String,
    val definitions : List<Definition>
)

data class Definition(
    val Definition : String,
    val Example : String? = null
)