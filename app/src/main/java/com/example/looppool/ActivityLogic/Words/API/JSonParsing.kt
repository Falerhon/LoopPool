package com.example.looppool.ActivityLogic.Words.API

import com.google.gson.annotations.SerializedName

data class DictionaryResponse(
    @SerializedName("word") val word : String,
    @SerializedName("meanings") val meanings: List<Meaning>
)

data class Meaning(
    @SerializedName("partOfSpeech") val partOfSpeach: String,
    @SerializedName("definitions") val definitions : List<Definition>
)

data class Definition(
    @SerializedName("definition") val Definition : String,
    @SerializedName("example") val Example : String? = null
)