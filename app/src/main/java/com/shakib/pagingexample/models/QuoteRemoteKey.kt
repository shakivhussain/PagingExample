package com.shakib.pagingexample.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class QuoteRemoteKey(

    @PrimaryKey(autoGenerate = false)

    val id: String,
    val prevKay: Int?,
    val nextKey: Int?,
)