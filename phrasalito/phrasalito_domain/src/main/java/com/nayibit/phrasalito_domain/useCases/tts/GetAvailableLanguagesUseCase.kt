package com.nayibit.phrasalito_domain.useCases.tts

import com.nayibit.common.util.Resource
import com.nayibit.phrasalito_domain.repository.TextSpeechRepository
import kotlinx.coroutines.flow.Flow
import java.util.Locale
import javax.inject.Inject

class GetAvailableLanguagesUseCase @Inject constructor(
    private val textToSpeechRepository: TextSpeechRepository
) {
    suspend operator fun invoke(): Flow<Resource<List<Locale>>> {
        return textToSpeechRepository.getLanguagesSuported()
    }

}