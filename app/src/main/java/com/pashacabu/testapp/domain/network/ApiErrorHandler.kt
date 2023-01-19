package com.pashacabu.testapp.domain.network

import com.pashacabu.testapp.domain.utils.ApiResult
import timber.log.Timber

interface ApiErrorHandler {
    suspend fun <T: Any> safeCall(block: suspend ()-> ApiResult<T> ): ApiResult<T>
}

class ApiErrorHandlerImpl(): ApiErrorHandler{
    override suspend fun <T: Any> safeCall(block: suspend () -> ApiResult<T>): ApiResult<T> {
        return try {
            block.invoke()
        } catch (e: Exception){
            Timber.e("Got EXCEPTION on API call: $e")
            ApiResult.Error()
        }
    }

}
