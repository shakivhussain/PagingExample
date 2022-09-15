package com.shakib.pagingexample.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shakib.pagingexample.models.Quote

@Dao
interface QuoteDao {

    @Query("SELECT * FROM quote")
    fun getQuotes(): PagingSource<Int, Quote>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addQuotes(list: List<Quote>)

    @Query("DELETE FROM quote")
    suspend fun deleteQuotes()
}