package com.nayibit.phrasalito_domain.useCases.dataStore


import com.nayibit.common.util.Constants.TUTORIAL_DECK
import com.nayibit.common.util.Resource
import com.nayibit.phrasalito_domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class IsTutorialDeckUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {

    operator fun invoke(): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading)
            dataStoreRepository.getData(TUTORIAL_DECK, true)
                .collect { value ->
                    emit(Resource.Success(value ?: true))
                }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }


}