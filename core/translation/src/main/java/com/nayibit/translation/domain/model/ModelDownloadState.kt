package com.nayibit.translation.domain.model

sealed class ModelDownloadState {
    object NotDownloaded : ModelDownloadState()
    object Downloading : ModelDownloadState()
    object Downloaded : ModelDownloadState()
    data class Error(val message: String) : ModelDownloadState()
}
