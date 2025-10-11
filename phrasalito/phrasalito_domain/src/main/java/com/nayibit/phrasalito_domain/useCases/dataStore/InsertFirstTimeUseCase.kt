package com.nayibit.phrasalito_domain.useCases.dataStore

import com.nayibit.common.util.Constants
import com.nayibit.common.util.Resource
import com.nayibit.phrasalito_domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class InsertFirstTimeUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {

    operator fun invoke() = flow {
        try {
            emit(Resource.Loading)
            dataStoreRepository.saveData(Constants.FIRST_TIME, true)
            emit(Resource.Success(true))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }

}