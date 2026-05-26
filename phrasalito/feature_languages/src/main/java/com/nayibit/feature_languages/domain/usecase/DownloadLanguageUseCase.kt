package com.nayibit.feature_languages.domain.usecase

import com.nayibit.feature_languages.domain.repository.LanguageRepository
import com.nayibit.translation.domain.model.ModelDownloadState
import com.nayibit.utils.helpers.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DownloadLanguageUseCase @Inject constructor(
    private val repository: LanguageRepository
) {
    operator fun invoke(code: String): Flow<Resource<ModelDownloadState>> =
        repository.downloadLanguage(code)
}
