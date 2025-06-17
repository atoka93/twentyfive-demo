package com.attilaszabo.twentyfivedemo.di

import android.content.Context
import com.attilaszabo.twentyfivedemo.quoteapi.QuotableApiClient
import com.attilaszabo.twentyfivedemo.quoteapi.QuotableApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.option.viewModelScopeFactory
import org.koin.core.qualifier.StringQualifier
import org.koin.core.qualifier.named
import org.koin.dsl.module

object AppDiModule {
    fun initialize(applicationContext: Context) {
        startKoin {
            options(
                viewModelScopeFactory()
            )
            androidLogger()
            androidContext(applicationContext)
            modules(
                globalDependencies,
                quoteModule(applicationContext)
            )
        }
    }
}

val IO_DISPATCHER_QUALIFIER: StringQualifier = named("ioDispatcher")

val globalDependencies = module {
    single<CoroutineDispatcher>(qualifier = IO_DISPATCHER_QUALIFIER) { Dispatchers.IO }
    single<QuotableApiService> { QuotableApiClient.service }
}
