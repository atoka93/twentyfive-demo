package com.attilaszabo.twentyfivedemo.quotedata

import com.attilaszabo.twentyfivedemo.quoteapi.QuoteRemoteDataSource
import com.attilaszabo.twentyfivedemo.quotecache.QuoteLocalDataSource
import com.attilaszabo.twentyfivedemo.quotedomain.Quote
import com.attilaszabo.twentyfivedemo.quotedomain.QuoteRepository
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.takeWhile
import com.attilaszabo.twentyfivedemo.commondomain.Result

class QuoteRepositoryImpl(
    private val remoteDataSource: QuoteRemoteDataSource,
    private val localDataSource: QuoteLocalDataSource,
    private val dispatcher: CoroutineDispatcher,
) : QuoteRepository {

    override fun obtainRandomQuote(): Flow<Result<Quote>> {
        val remoteEmitted = CompletableDeferred<Boolean>()

        val localFlow: Flow<Result<Quote>> = localDataSource.quoteFlow
            .takeWhile { !remoteEmitted.isCompleted }
            .mapNotNull { Result.Success(it.mapToDomain()) }
        val remoteFlow: Flow<Result<Quote>> = remoteDataSource.fetchRandomQuote()
            .map { dto ->
                if (!remoteEmitted.isCompleted) {
                    remoteEmitted.complete(true)
                }
                dto?.run {
                    localDataSource.saveQuote(mapToLocalDto())
                }
                if (dto == null) {
                    Result.Error();
                } else {
                    Result.Success(dto.mapToDomain());
                }
            }
            .catch { _ ->
                emit(Result.Error())
            }

        return merge(localFlow, remoteFlow).flowOn(dispatcher)
    }
}
