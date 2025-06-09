package com.attilaszabo.twentyfivedemo.quoteapi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object QuotableApiClient {
    // https://github.com/lukePeavey/quotable
    private const val BASE_URL = "https://api.quotable.io/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: QuotableApiService = retrofit.create(QuotableApiService::class.java)
}
