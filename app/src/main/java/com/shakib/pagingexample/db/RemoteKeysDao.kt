package com.shakib.pagingexample.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shakib.pagingexample.models.Quote
import com.shakib.pagingexample.models.QuoteRemoteKey

@Dao
interface RemoteKeysDao {

    @Query("SELECT * FROM remote_keys WHERE id=:id")
    fun getRemoteKeys(id : String): QuoteRemoteKey

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(list: List<QuoteRemoteKey>)

    @Query("DELETE FROM remote_keys")
    suspend fun deleteRemoteKeys()
}