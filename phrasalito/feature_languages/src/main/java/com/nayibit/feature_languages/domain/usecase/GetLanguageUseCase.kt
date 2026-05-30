package com.nayibit.feature_languages.domain.usecase

import com.nayibit.feature_languages.domain.model.Language
import com.nayibit.feature_languages.domain.repository.LanguageRepository
import com.nayibit.network.error.NetworkError
import com.nayibit.utils.helpers.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLanguagesUseCase @Inject constructor(
    private val repository: LanguageRepository
) {
    operator fun invoke(): Flow<Result<List<Language>, NetworkError>> =
        repository.getLanguages()
}
