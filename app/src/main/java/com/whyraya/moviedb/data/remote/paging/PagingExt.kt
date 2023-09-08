package com.whyraya.moviedb.data.remote.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig

/**
 * https://medium.com/@raya.wahyu.anggara/generic-paging-source-for-android-pagination-b5bbf55f0c81
 * */
fun <V : Any> withPager(request: suspend (Int) -> List<V>) = createPager(
    pageSize = LIMIT,
    prefetchDistance = PRE_FETCH_DISTANCE
) {
    request(it)
}

fun <V : Any> createPager(
    pageSize: Int = 0,
    prefetchDistance: Int = 0,
    enablePlaceholders: Boolean = false,
    request: suspend (Int) -> List<V>
): Pager<Int, V> = Pager(
    config = PagingConfig(
        enablePlaceholders = enablePlaceholders,
        pageSize = pageSize,
        prefetchDistance = prefetchDistance
    ),
    pagingSourceFactory = { BasePagingSource(request) }
)

private const val LIMIT = 10
private const val PRE_FETCH_DISTANCE = 2
