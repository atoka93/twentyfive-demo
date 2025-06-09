package com.attilaszabo.twentyfivedemo.quotedata

import com.attilaszabo.twentyfivedemo.quoteapi.QuoteResponseDto
import com.attilaszabo.twentyfivedemo.quotedomain.QuoteResponse

object QuoteResponseMapper {
    fun mapToDomain(quoteResponseDto: QuoteResponseDto): QuoteResponse {
        return QuoteResponse(
            quoteResponseDto._id,
            quoteResponseDto.content,
            quoteResponseDto.author,
            quoteResponseDto.tags,
        )
    }

    fun mapToDto(quoteResponse: QuoteResponse): QuoteResponseDto {
        return QuoteResponseDto(
            quoteResponse.id,
            quoteResponse.content,
            quoteResponse.author,
            "",
            quoteResponse.content.length,
            quoteResponse.tags,
        )
    }
}