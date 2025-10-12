package com.nayibit.phrasalito_domain.useCases.dataStore

import android.util.Log
import com.nayibit.common.util.Constants
import com.nayibit.common.util.Constants.FIRST_TIME
import com.nayibit.common.util.Resource
import com.nayibit.phrasalito_domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFirstTimeUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    operator fun invoke(): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading)
            dataStoreRepository.getData(FIRST_TIME, false)
                .collect { value ->
                    emit(Resource.Success(value ?: false))
                }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }

}