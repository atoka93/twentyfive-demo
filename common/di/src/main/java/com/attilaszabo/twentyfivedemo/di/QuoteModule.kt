package com.attilaszabo.twentyfivedemo.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import com.attilaszabo.twentyfivedemo.quoteapi.QuoteRemoteDataSource
import com.attilaszabo.twentyfivedemo.quoteapi.QuoteRemoteDataSourceImpl
import com.attilaszabo.twentyfivedemo.quotecache.QuoteLocalDataSource
import com.attilaszabo.twentyfivedemo.quotecache.QuoteLocalDataSourceImpl
import com.attilaszabo.twentyfivedemo.quotecache.QuoteProto
import com.attilaszabo.twentyfivedemo.quotecache.QuoteSerializer
import com.attilaszabo.twentyfivedemo.quote.data.QuoteRepositoryImpl
import com.attilaszabo.twentyfivedemo.quote.domain.GetRandomQuoteUseCase
import com.attilaszabo.twentyfivedemo.quote.domain.QuoteRepository
import com.attilaszabo.twentyfivedemo.quote.presentation.QuoteViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.scopedOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import java.io.File

fun quoteModule(context: Context): Module =
    module {
        viewModelOf(::QuoteViewModel)
        scope<QuoteViewModel> {
            scopedOf(::GetRandomQuoteUseCase)
        }

        single<QuoteRepository> {
            QuoteRepositoryImpl(
                remoteDataSource = get(),
                localDataSource = get(),
                dispatcher = get(qualifier = IO_DISPATCHER_QUALIFIER),
            )
        }
        singleOf(::QuoteRemoteDataSourceImpl) bind QuoteRemoteDataSource::class
        singleOf(::QuoteLocalDataSourceImpl) bind QuoteLocalDataSource::class
        single<DataStore<QuoteProto>> {
            DataStoreFactory.create(
                serializer = QuoteSerializer,
                produceFile = { File(context.filesDir, "quote.pb") }
            )
        }
    }
