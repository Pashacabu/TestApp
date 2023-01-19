package com.pashacabu.testapp.domain.utils

sealed class ApiResult<T>(val data: T? = null, val code: Int? = null) {
    class Success<T>(data: T?, code: Int?) : ApiResult<T>(data, code)
    class Error<T>(data: T? = null, code: Int? = null) : ApiResult<T>(data, code)
}

fun ApiResult<*>.isSuccessful(): Boolean {
    return this is ApiResult.Success
}