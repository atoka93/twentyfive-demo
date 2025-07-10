package com.attilaszabo.twentyfivedemo.quoteapi

data class QuoteResponseDto(
    val _id: String,
    val content: String,
    val author: String,
    val authorSlug: String,
    val length: Int,
    val tags: List<String>
)
