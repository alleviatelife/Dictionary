package com.example.dictionarycompose.domain.usecase.wordInfo

import com.example.dictionarycompose.common.Resource
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

}