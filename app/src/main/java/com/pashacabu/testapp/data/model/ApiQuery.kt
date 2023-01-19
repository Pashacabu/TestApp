package com.pashacabu.testapp.data.model

sealed class ApiQuery {

    sealed class Paired(val key: String, val value: Any): ApiQuery(){

        data class Search(val field: String, val condition: Any): Paired(field, condition)
        data class Sort(val field: String, val order: SortOrder): Paired(field, order.order)

        sealed class SortOrder(val order: Int){
            object Asc: SortOrder(1)
            object Desc: SortOrder(-1)
        }

    }

    data class Page(val number: Int = 1): ApiQuery()
    data class Limit(val perPage: Int = 10): ApiQuery()

}
