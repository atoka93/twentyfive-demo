package com.attilaszabo.twentyfivedemo.quotecache

data class QuoteLocalDto(
    val id: String,
    val content: String,
    val author: String,
    val tags: List<String>
)
