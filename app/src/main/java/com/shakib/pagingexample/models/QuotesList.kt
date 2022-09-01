package com.shakib.pagingexample.models

data class QuotesList(
    val count: Int,
    val lastItemIndex: Int,
    val page: Int,
    val results: List<Quotes>, //quotes
    val totalCount: Int,
    val totalPages: Int
)