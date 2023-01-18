package com.pashacabu.testapp.domain.mappers

import com.pashacabu.testapp.domain.utils.ApiResult
import retrofit2.Response

interface ApiMapper<F : Response<out Any?>, T : ApiResult<out Any?>> {

    fun transform(from: F): T
}