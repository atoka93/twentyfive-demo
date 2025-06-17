package com.attilaszabo.twentyfivedemo.quotedomain

data class Quote(
    val id: String,
    val content: String,
    val author: String,
    val tags: List<String>
)
