package com.ie.feature_startscreen.domain.usecases

import com.ie.feature_startscreen.utils.Constants.FIRST_TIME
import com.nayibit.datastore.domain.repositories.DataStoreRepository
import com.nayibit.utils.helpers.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFirstTimeUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    operator fun invoke(): Flow<Resource<Boolean>> = flow {
        try {
            dataStoreRepository.getData(FIRST_TIME, false)
                .collect { value ->
                    emit(Resource.Success(value ?: false))
                }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }

}