package com.example.dictionarycompose.domain.usecase.wordInfo

import com.example.dictionarycompose.common.Resource
import com.example.dictionarycompose.data.remote.dto.WordInfoDTO
import com.example.dictionarycompose.data.remote.dto.toWordInfo
import com.example.dictionarycompose.domain.models.ErrorModel
import com.example.dictionarycompose.domain.models.WordInfo
import com.example.dictionarycompose.domain.repository.WordInfoRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetWordInfoUseCase @Inject constructor(
    private val wordInfoRepository: WordInfoRepository
) {
    operator fun invoke(word: String): Flow<Resource<List<WordInfo>>> = flow {
        try {
            emit(Resource.Loading())
//            val gson = Gson()
//            val type = object : TypeToken<List<WordInfoDTO>>() {}.type
//            var response: List<WordInfoDTO> = gson.fromJson(json, type)

            val wordInfo = wordInfoRepository.getWordInfo(word)
            emit(Resource.Success(wordInfo.map { it.toWordInfo() }))
        } catch (e: HttpException) {

            val gson = Gson()
            val type = object : TypeToken<ErrorModel>() {}.type
            var errorResponse: ErrorModel? = gson.fromJson(e.response()?.errorBody()!!.charStream(), type)
            emit(Resource.Error(message = e.localizedMessage +" : "+ errorResponse?.title ?:"An unexpected error occurred"))

        } catch (e: IOException) {

            emit(Resource.Error(message = e.localizedMessage?:"Couldn't reach server"))

        }

    }


    val json:String="[\n" +
            "  {\n" +
            "    \"word\": \"hello\",\n" +
            "    \"phonetic\": \"həˈləʊ\",\n" +
            "    \"phonetics\": [\n" +
            "      {\n" +
            "        \"text\": \"həˈləʊ\",\n" +
            "        \"audio\": \"//ssl.gstatic.com/dictionary/static/sounds/20200429/hello--_gb_1.mp3\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"text\": \"hɛˈləʊ\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"origin\": \"early 19th century: variant of earlier hollo ; related to holla.\",\n" +
            "    \"meanings\": [\n" +
            "      {\n" +
            "        \"partOfSpeech\": \"exclamation\",\n" +
            "        \"definitions\": [\n" +
            "          {\n" +
            "            \"definition\": \"used as a greeting or to begin a phone conversation.\",\n" +
            "            \"example\": \"hello there, Katie!\",\n" +
            "            \"synonyms\": [],\n" +
            "            \"antonyms\": []\n" +
            "          }\n" +
            "        ]\n" +
            "      },\n" +
            "      {\n" +
            "        \"partOfSpeech\": \"noun\",\n" +
            "        \"definitions\": [\n" +
            "          {\n" +
            "            \"definition\": \"an utterance of ‘hello’; a greeting.\",\n" +
            "            \"example\": \"she was getting polite nods and hellos from people\",\n" +
            "            \"synonyms\": [],\n" +
            "            \"antonyms\": []\n" +
            "          }\n" +
            "        ]\n" +
            "      },\n" +
            "      {\n" +
            "        \"partOfSpeech\": \"verb\",\n" +
            "        \"definitions\": [\n" +
            "          {\n" +
            "            \"definition\": \"say or shout ‘hello’.\",\n" +
            "            \"example\": \"I pressed the phone button and helloed\",\n" +
            "            \"synonyms\": [],\n" +
            "            \"antonyms\": []\n" +
            "          }\n" +
            "        ]\n" +
            "      }\n" +
            "    ]\n" +
            "  }\n" +
            "]\n"

}