package com.nayibit.database.utils
import com.nayibit.utils.helpers.Error

sealed interface DatabaseError: Error {
    data object Unknown : DatabaseError
    data object ProtectedCategory : DatabaseError
    data class Sql(val throwable: Throwable) : DatabaseError
}
