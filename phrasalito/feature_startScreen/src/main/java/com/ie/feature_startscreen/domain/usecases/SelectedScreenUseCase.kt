package com.ie.feature_startscreen.domain.usecases

import com.ie.feature_startscreen.utils.Constants.FIRST_TIME
import com.nayibit.utils.SelectScreen
import com.nayibit.datastore.domain.repositories.DataStoreRepository
import com.nayibit.datastore.utils.getData
import com.nayibit.utils.DataStoreKeys.SELECTED_LANGUAGE_ID
import com.nayibit.utils.helpers.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SelectedScreenUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    operator fun invoke(): Flow<Resource<SelectScreen>> {
        return combine(
            dataStoreRepository.getData(FIRST_TIME, false),
            dataStoreRepository.getData(SELECTED_LANGUAGE_ID, 0)
        ) { firstTime: Boolean, selectedLanguage: Int ->

            when {
                !firstTime -> SelectScreen.START_SCREEN
                selectedLanguage == 0 -> SelectScreen.LANGUAGE_SCREEN
                else -> SelectScreen.CATEGORIE_SCREEN
            }
        }.map { screen ->
            Resource.Success(screen) as Resource<SelectScreen>
        }.catch { e ->
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }
  }
