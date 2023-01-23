package com.pashacabu.testapp.data.model

sealed class ApiQuery {

    sealed class Paired(val key: String, val value: Any) : ApiQuery() {

        data class Search(val field: ApiField, val condition: Any) : Paired(field.value, condition)
        data class Sort(val field: ApiField, val order: SortOrder) : Paired(field.value, order.order)

        sealed class SortOrder(val order: Int) {
            object Asc : SortOrder(1)
            object Desc : SortOrder(-1)
        }

    }

    data class Page(val number: Int) : ApiQuery()
    data class Limit(val perPage: Int) : ApiQuery()

    companion object {
        fun constructQuery(
            searchField: ApiField? = null,
            searchCondition: String? = null,
            sortField: ApiField? = null,
            sortOrder: Paired.SortOrder? = null,
            limitPerPage: Int = 10,
            page: Int = 1
        ): List<ApiQuery> {
            val out = mutableListOf<ApiQuery>()
            searchField?.let { field ->
                searchCondition?.let { cond ->
                    out.add(Paired.Search(field,cond))
                }
            }
            sortField?.let { field ->
                (sortOrder ?: Paired.SortOrder.Asc).let { ord ->
                    out.add(Paired.Sort(field, ord))
                }
            }
            out.add(Limit(limitPerPage))
            out.add(Page(page))
            return out.toList()
        }
    }

}
