package com.attilaszabo.twentyfivedemo.di

import com.attilaszabo.twentyfivedemo.quoteapi.QuotableApiClient
import com.attilaszabo.twentyfivedemo.quoteapi.QuotableApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class QuoteApi {
    @Provides
    @Singleton
    fun provideQuotableApi(): QuotableApiService =
        QuotableApiClient.service

}
