package com.shakib.pagingexample.models

data class QuotesResponse(
    val count: Int,
    val lastItemIndex: Int,
    val page: Int,
    val results: List<Quote>, //quotes
    val totalCount: Int,
    val totalPages: Int
)