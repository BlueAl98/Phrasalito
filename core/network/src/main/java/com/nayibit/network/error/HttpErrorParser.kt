package com.nayibit.network.error

import com.google.gson.Gson
import com.nayibit.network.model.ApiErrorDto
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

object HttpErrorParser {

    private val gson = Gson()

    fun parse(throwable: Throwable): NetworkError = when (throwable) {
        is SocketTimeoutException -> NetworkError.Timeout
        is IOException -> NetworkError.NoInternet
        is HttpException -> parseHttp(throwable)
        else -> NetworkError.Unknown
    }

    private fun parseHttp(e: HttpException): NetworkError.Http {
        val raw = e.response()?.errorBody()?.string()
        val dto = try { gson.fromJson(raw, ApiErrorDto::class.java) } catch (_: Exception) { null }
        val message = dto?.message?.takeIf { it.isNotBlank() } ?: spanishFallback(e.code())
        return NetworkError.Http(status = e.code(), message = message)
    }

    private fun spanishFallback(status: Int): String = when (status) {
        400 -> "Solicitud inválida"
        401 -> "No autorizado. Por favor inicia sesión"
        403 -> "No tienes permiso para acceder a este recurso"
        404 -> "El recurso solicitado no existe"
        409 -> "Conflicto con el estado actual del recurso"
        422 -> "Los datos enviados no son válidos"
        429 -> "Demasiadas solicitudes. Intenta de nuevo más tarde"
        500 -> "Error interno del servidor"
        502 -> "El servicio no está disponible"
        503 -> "Servicio temporalmente fuera de servicio"
        else -> "Ocurrió un error inesperado (código $status)"
    }
}
