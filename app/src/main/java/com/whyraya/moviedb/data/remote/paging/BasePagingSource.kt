package com.whyraya.moviedb.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState

/**
 * https://medium.com/@raya.wahyu.anggara/generic-paging-source-for-android-pagination-b5bbf55f0c81
 * */
class BasePagingSource<V : Any>(
    private val block: suspend (Int) -> List<V>
) : PagingSource<Int, V>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, V> {
        val pageNumber = params.key ?: 1
        return try {
            val response = block(pageNumber)
            LoadResult.Page(
                data = response,
                prevKey = if (response.isEmpty() || pageNumber == 1) null else pageNumber.dec(),
                nextKey = if (response.isEmpty()) null else pageNumber.inc()
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, V>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
}
