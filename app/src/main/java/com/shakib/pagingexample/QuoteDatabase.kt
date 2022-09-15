package com.shakib.pagingexample

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shakib.pagingexample.db.QuoteDao
import com.shakib.pagingexample.db.RemoteKeysDao
import com.shakib.pagingexample.models.Quote
import com.shakib.pagingexample.models.QuoteRemoteKey

@Database(entities = [Quote::class, QuoteRemoteKey::class], version = 1)
abstract class QuoteDatabase : RoomDatabase() {

    abstract fun quoteDao(): QuoteDao
    abstract fun remoteKeysDao(): RemoteKeysDao

}