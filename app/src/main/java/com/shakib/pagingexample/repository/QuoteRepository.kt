package com.shakib.pagingexample.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.shakib.pagingexample.QuoteDatabase
import com.shakib.pagingexample.paging.QuotePagingSource
import com.shakib.pagingexample.paging.QuoteRemoteMediator
import com.shakib.pagingexample.retrofit.QuoteApi
import javax.inject.Inject

// provide data in the form of live data, data will provide paging library.
@OptIn(ExperimentalPagingApi::class)
class QuoteRepository @Inject constructor(
    val quoteApi: QuoteApi,
    private val quoteDatabase: QuoteDatabase
) {

    // pageSize = no of page load at a time
    // maxSize = after that no the records will starting clearing
    fun getQuotes() = Pager(
        config = PagingConfig(pageSize = 20, maxSize = 100),
        remoteMediator = QuoteRemoteMediator(quoteApi, quoteDatabase),
        pagingSourceFactory = { quoteDatabase.quoteDao().getQuotes() }
    ).liveData // or flow
}