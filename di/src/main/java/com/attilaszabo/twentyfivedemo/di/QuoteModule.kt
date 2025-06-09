package com.attilaszabo.twentyfivedemo.di

import com.attilaszabo.twentyfivedemo.quoteapi.QuotableApiService
import com.attilaszabo.twentyfivedemo.quoteapi.QuoteRemoteDataSource
import com.attilaszabo.twentyfivedemo.quoteapi.QuoteRemoteDataSourceImpl
import com.attilaszabo.twentyfivedemo.quotedata.QuoteRepositoryImpl
import com.attilaszabo.twentyfivedemo.quotedomain.GetRandomQuoteUseCase
import com.attilaszabo.twentyfivedemo.quotedomain.QuoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object QuoteModule {
    @Provides
    fun provideQuoteRemoteDataSource(api: QuotableApiService): QuoteRemoteDataSource =
        QuoteRemoteDataSourceImpl(api)

    @Provides
    fun provideQuoteRepository(remoteDataSource: QuoteRemoteDataSource): QuoteRepository =
        QuoteRepositoryImpl(remoteDataSource)

    @Provides
    fun provideGetRandomQuoteUseCase(repository: QuoteRepository): GetRandomQuoteUseCase =
        GetRandomQuoteUseCase(repository)
}