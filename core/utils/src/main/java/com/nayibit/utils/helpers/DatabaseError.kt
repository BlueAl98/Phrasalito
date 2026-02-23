package com.nayibit.utils.helpers

sealed interface DatabaseError: Error {
    data object Unknown : DatabaseError
    data class Sql(val throwable: Throwable) : DatabaseError
}
