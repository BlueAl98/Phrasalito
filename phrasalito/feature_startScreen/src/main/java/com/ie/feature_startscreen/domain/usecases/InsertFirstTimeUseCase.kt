package com.ie.feature_startscreen.domain.usecases

import com.ie.feature_startscreen.utils.Constants.FIRST_TIME
import com.nayibit.datastore.domain.repositories.DataStoreRepository
import com.nayibit.utils.helpers.Resource
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class InsertFirstTimeUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {

    operator fun invoke() = flow {
        try {
            dataStoreRepository.saveData(FIRST_TIME, true)
            emit(Resource.Success(true))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }

}