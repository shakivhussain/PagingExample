package com.shakib.pagingexample.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.shakib.pagingexample.paging.QuotePagingSource
import com.shakib.pagingexample.retrofit.QuoteApi
import javax.inject.Inject

// Step 6
// provide data in the form of live data, data will provide paging library.
class QuoteRepository @Inject constructor(val quoteApi: QuoteApi) {

    // pageSize = no of page load at a time
    // maxSize = after that no the records will starting clearing
    fun getQuotes() = Pager(
        config = PagingConfig(pageSize = 20, maxSize = 100),
        pagingSourceFactory = { QuotePagingSource(quoteApi) }
    ).liveData // or flow
}