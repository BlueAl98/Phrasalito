package com.nayibit.network.interceptors

import okhttp3.logging.HttpLoggingInterceptor

internal fun provideLoggingInterceptor(isDebug: Boolean): HttpLoggingInterceptor =
    HttpLoggingInterceptor().apply {
        level = if (isDebug) HttpLoggingInterceptor.Level.BODY
                else HttpLoggingInterceptor.Level.NONE
    }
