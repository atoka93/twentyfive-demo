package com.attilaszabo.twentyfivedemo.quote.data

import com.attilaszabo.twentyfivedemo.common.domain.CustomResult
import com.attilaszabo.twentyfivedemo.quoteapi.QuoteRemoteDataSource
import com.attilaszabo.twentyfivedemo.quotecache.QuoteLocalDataSource
import com.attilaszabo.twentyfivedemo.quote.domain.models.Quote
import com.attilaszabo.twentyfivedemo.quote.domain.QuoteRepository
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.takeWhile

class QuoteRepositoryImpl(
    private val remoteDataSource: QuoteRemoteDataSource,
    private val localDataSource: QuoteLocalDataSource,
    private val dispatcher: CoroutineDispatcher,
) : QuoteRepository {

    override fun obtainRandomQuote(): Flow<CustomResult<Quote>> {
        val remoteEmitted = CompletableDeferred<Boolean>()

        val localFlow: Flow<CustomResult<Quote>> = localDataSource.quoteFlow
            .takeWhile { !remoteEmitted.isCompleted }
            .mapNotNull { CustomResult.success(it.mapToDomain()) }
        val remoteFlow: Flow<CustomResult<Quote>> = remoteDataSource.fetchRandomQuote()
            .map { dto ->
                if (!remoteEmitted.isCompleted) {
                    remoteEmitted.complete(true)
                }
                dto?.run {
                    localDataSource.saveQuote(mapToLocalDto())
                }
                if (dto == null) {
                    CustomResult.failure()
                } else {
                    CustomResult.success(dto.mapToDomain())
                }
            }
            .catch { cause ->
                emit(CustomResult.failure(cause))
            }

        return merge(localFlow, remoteFlow).flowOn(dispatcher)
    }
}
