package com.pashacabu.testapp.domain.network

import androidx.compose.runtime.mutableStateOf
import com.pashacabu.testapp.data.model.ApiQuery
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

interface QueryInterceptor: Interceptor {
    fun setQueries(queries: List<ApiQuery>)
    fun clearQueries()
}

class QueryInterceptorImpl(): QueryInterceptor {

    private var queries = mutableStateOf<List<ApiQuery>>(listOf())

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        val urlBuilder = request.url.newBuilder()
            queries.value.forEach{ processQueryParam(urlBuilder, it) }
        request = request.newBuilder()
            .url(urlBuilder.build())
            .build()
        return chain.proceed(request)
    }

    override fun setQueries(queries: List<ApiQuery>) {
        this.queries.value = queries
    }

    override fun clearQueries() {
        queries.value = listOf()
    }

    private fun processQueryParam(builder: HttpUrl.Builder, param: ApiQuery){
        when (param){
            is ApiQuery.Limit -> builder.addQueryParameter(LIMIT, param.perPage.toString())
            is ApiQuery.Page -> builder.addQueryParameter(PAGE, param.number.toString())
            is ApiQuery.Paired.Search -> {
                builder.addQueryParameter(FIELD, param.field.value)
                builder.addQueryParameter(SEARCH, param.condition.toString())
            }
            is ApiQuery.Paired.Sort -> {
                builder.addQueryParameter(SORT_FIELD, param.field.value)
                builder.addQueryParameter(SORT_TYPE, param.order.order.toString())
            }
        }
    }

    companion object {
        const val FIELD = "field"
        const val SEARCH = "search"
        const val LIMIT = "limit"
        const val PAGE = "page"
        const val SORT_FIELD = "sortField"
        const val SORT_TYPE = "sortType"
    }

}