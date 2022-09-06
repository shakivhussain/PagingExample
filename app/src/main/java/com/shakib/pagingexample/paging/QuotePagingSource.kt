package com.shakib.pagingexample.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.shakib.pagingexample.models.Quote
import com.shakib.pagingexample.models.QuotesResponse
import com.shakib.pagingexample.retrofit.QuoteApi


// Step 5                                               key = pageNo , value = resultModel
class QuotePagingSource(private val api: QuoteApi) : PagingSource<Int, Quote>() {

    // here we hit our api and get data
    // if library want to load data then call load func.
    // it check here page no exists or not , otherwise page 1 load.
    // and we have to create PAGE obj and pass 3 values.
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Quote> {
        return try {
            val position = params.key ?: 1 // which page load, contains info
            val response: QuotesResponse = api.getQuotes(position)

            LoadResult.Page(
                data = response.results,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (position == response.totalPages) null else position + 1
            )

        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("TAG", "load: ${e.message}")
            LoadResult.Error(e)
        }


    }

    // help to load next page by the help of logic.
    // if getrerresh method return null then 1 page load
    override fun getRefreshKey(state: PagingState<Int, Quote>): Int? {
        // anchorPos = recenlty access page, stored in val
        // closestPageToPos =  find a closses page, which pos you pass around that posiotion.

        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1) ?: state.closestPageToPosition(
                it
            )?.nextKey?.minus(1)
        }

        // OR

//        if (state.anchorPosition != null) {
//            val anchorPos = state.closestPageToPosition(state.anchorPosition!!)
//            if (anchorPos?.prevKey != null) {
//                return anchorPos.prevKey!!.plus(1)
//            } else if (anchorPos?.nextKey != null) {
//                return anchorPos.nextKey!!.minus(1)
//            } else {
//                return null
//            }
//        } else {
//            return null
//        }
    }
}