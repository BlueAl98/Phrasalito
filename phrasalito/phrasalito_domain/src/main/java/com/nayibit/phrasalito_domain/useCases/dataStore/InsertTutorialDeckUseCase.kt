package com.nayibit.phrasalito_domain.useCases.dataStore

import com.nayibit.common.util.Constants.TUTORIAL_DECK
import com.nayibit.phrasalito_domain.repository.DataStoreRepository
import javax.inject.Inject

class InsertTutorialDeckUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke() {
        dataStoreRepository.saveData(TUTORIAL_DECK, false)
    }

}