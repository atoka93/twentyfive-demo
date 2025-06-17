package com.attilaszabo.twentyfivedemo.quotedata

import com.attilaszabo.twentyfivedemo.quoteapi.QuoteResponseDto
import com.attilaszabo.twentyfivedemo.quotecache.QuoteLocalDto
import com.attilaszabo.twentyfivedemo.quotedomain.Quote

fun QuoteLocalDto.mapToDomain(): Quote {
    return Quote(
        id,
        content,
        author,
        tags,
    )
}

fun QuoteResponseDto.mapToLocalDto(): QuoteLocalDto {
    return QuoteLocalDto(
        _id,
        content,
        author,
        tags,
    )
}

fun QuoteResponseDto.mapToDomain(): Quote {
    return Quote(
        _id,
        content,
        author,
        tags,
    )
}

fun Quote.mapToResponseDto(): QuoteResponseDto {
    return QuoteResponseDto(
        id,
        content,
        author,
        "",
        content.length,
        tags,
    )
}
