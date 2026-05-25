package com.nayibit.feature_languages.domain.usecase

import com.nayibit.feature_languages.domain.model.Language
import com.nayibit.feature_languages.domain.repository.LanguageRepository
import javax.inject.Inject

class GetLanguageUseCase @Inject constructor(
    private val repository: LanguageRepository
) {
    suspend operator fun invoke(id: Int): Result<Language> = runCatching {
        repository.getLanguage(id)
    }
}
