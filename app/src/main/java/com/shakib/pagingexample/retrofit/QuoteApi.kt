package com.shakib.pagingexample.retrofit

import com.shakib.pagingexample.models.QuotesList
import retrofit2.http.GET
import retrofit2.http.Query

interface QuoteApi {

    @GET("/quotes")
    suspend fun getQuotes(@Query("page") page: Int): QuotesList
}