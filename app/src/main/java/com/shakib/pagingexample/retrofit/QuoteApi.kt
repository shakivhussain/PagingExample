package com.shakib.pagingexample.retrofit

import com.shakib.pagingexample.models.QuotesResponse
import dagger.Provides
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton


interface QuoteApi {

    @GET("/quotes")
    suspend fun getQuotes(@Query("page") page: Int): QuotesResponse

}