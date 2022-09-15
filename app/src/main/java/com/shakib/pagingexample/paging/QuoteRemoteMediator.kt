package com.shakib.pagingexample.paging

import androidx.paging.*
import androidx.room.withTransaction
import com.shakib.pagingexample.QuoteDatabase
import com.shakib.pagingexample.models.Quote
import com.shakib.pagingexample.models.QuoteRemoteKey
import com.shakib.pagingexample.retrofit.QuoteApi

@OptIn(ExperimentalPagingApi::class)
class QuoteRemoteMediator(
    private val quoteApi: QuoteApi,
    private val quoteDatabase: QuoteDatabase
) : RemoteMediator<Int, Quote>() {

    val quoteDao = quoteDatabase.quoteDao()
    val remoteKeysDao = quoteDatabase.remoteKeysDao()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Quote>): MediatorResult {
        // 1. Fetch Quotes from api
        // 2. Save these quotes + Remote keys into the database
        // 3. Login for states - REFRESH, APPEND, PREPEND

        return try {

//            val currentPage = 1

            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    quoteDao.deleteQuotes()

                    val remoteKeys=getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextKey?.minus(1)?:1
                }

                LoadType.PREPEND -> {

                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val prevKeys = remoteKeys?.prevKay

                    if (prevKeys==null){
                        MediatorResult.Success(endOfPaginationReached = remoteKeys!=null)
                    }
                    prevKeys

                }
                LoadType.APPEND -> {
//                    state : all the page are manage in the state.
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextKey

                    if (nextPage==null){
                        MediatorResult.Success(endOfPaginationReached = remoteKeys!=null)
                    }
                    nextPage
                }

            }




            val response = quoteApi.getQuotes(currentPage?:1)
            val endOfPagination = response.totalPages == currentPage

            val prevPage = if (currentPage == 1) null else currentPage?.minus(1)
            val nextPage = if (endOfPagination) null else currentPage?.plus(1)

            // all the quotes and remote key save in transaction, bcz if any error, then quote and key data will not save into db
            quoteDatabase.withTransaction {
                if (loadType==LoadType.REFRESH){
                    quoteDao.deleteQuotes()
                    remoteKeysDao.deleteRemoteKeys()
                }

                quoteDao.addQuotes(response.results)
                val keys = response.results.map { quote ->

                    QuoteRemoteKey(
                        id = quote._id,
                        nextKey = nextPage,
                        prevKay = prevPage
                    )

                }
                remoteKeysDao.addAllRemoteKeys(keys)

            }

            MediatorResult.Success(endOfPaginationReached = true)
        } catch (e: Exception) {
            e.printStackTrace()
            MediatorResult.Error(e)
        }

    }

//    private suspend fun getRemoteKeyForTheLastResult(
//        state: PagingState<Int, Quote>
//    ): QuoteRemoteKey? {
//        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { quote ->
//            remoteKeysDao.getRemoteKeys(id = quote._id)
//        }
//
//    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Quote>): QuoteRemoteKey? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { repo ->
                // Get the remote keys of the last item retrieved
                remoteKeysDao.getRemoteKeys(repo._id)
            }
    }


    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Quote>): QuoteRemoteKey? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { repo ->
                // Get the remote keys of the first items retrieved
                remoteKeysDao.getRemoteKeys(repo._id)
            }
    }



    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Quote>
    ): QuoteRemoteKey? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?._id?.let { repoId ->
                remoteKeysDao.getRemoteKeys(repoId)
            }
        }
    }

}