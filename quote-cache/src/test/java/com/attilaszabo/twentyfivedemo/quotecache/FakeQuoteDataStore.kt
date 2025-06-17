package com.attilaszabo.twentyfivedemo.quotecache

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeQuoteDataStore(
    private val outputQuote: QuoteProto?
) : DataStore<QuoteProto> {
    var inputQuote: QuoteProto? = null

    override val data: Flow<QuoteProto> =
        flow {
            if (outputQuote != null) {
                emit(outputQuote)
            }
        }

    override suspend fun updateData(transform: suspend (t: QuoteProto) -> QuoteProto): QuoteProto {
        inputQuote = transform(inputQuote ?: QuoteProto.getDefaultInstance())
        return inputQuote!!
    }
}
