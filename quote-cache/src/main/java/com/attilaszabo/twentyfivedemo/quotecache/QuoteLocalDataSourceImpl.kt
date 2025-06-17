package com.attilaszabo.twentyfivedemo.quotecache

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull

class QuoteLocalDataSourceImpl(
    private val dataStore: DataStore<QuoteProto>,
) : QuoteLocalDataSource {

    override val quoteFlow: Flow<QuoteLocalDto> = dataStore.data.mapNotNull {
            QuoteLocalDto(
                it.id,
                it.content,
                it.author,
                it.tagsList.toList()
            )
    }

    override suspend fun saveQuote(quote: QuoteLocalDto) {
        dataStore.updateData {
            QuoteProto.newBuilder()
                .setId(quote.id)
                .setContent(quote.content)
                .setAuthor(quote.author)
                .addAllTags(quote.tags)
                .build()
        }
    }
}
