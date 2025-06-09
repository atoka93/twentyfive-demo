package com.attilaszabo.twentyfivedemo.quotedomain

data class QuoteResponse(
    val id: String,
    val content: String,
    val author: String,
    val tags: List<String>
)
