package com.nayibit.phrasalito_domain.useCases.dataStore


import com.nayibit.common.util.Constants.TUTORIAL_DECK
import com.nayibit.common.util.Resource
import com.nayibit.phrasalito_domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class IsTutorialDeckUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {

    suspend operator fun invoke(): Resource<Boolean>{
        try {
            val isTutorial = dataStoreRepository.getData(TUTORIAL_DECK, true).first()
            return Resource.Success(isTutorial ?: false)
        }catch (e: Exception){
            return Resource.Error(e.localizedMessage ?: "Unknown error")
        }
    }

}