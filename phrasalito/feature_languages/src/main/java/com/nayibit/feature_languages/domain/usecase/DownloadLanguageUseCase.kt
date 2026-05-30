package com.nayibit.feature_languages.domain.usecase

import com.nayibit.feature_languages.domain.repository.LanguageRepository
import com.nayibit.translation.domain.error.TranslationError
import com.nayibit.translation.domain.model.ModelDownloadState
import com.nayibit.utils.helpers.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DownloadLanguageUseCase @Inject constructor(
    private val repository: LanguageRepository
) {
    operator fun invoke(code: String): Flow<Result<ModelDownloadState, TranslationError>> =
        repository.downloadLanguage(code)
}
