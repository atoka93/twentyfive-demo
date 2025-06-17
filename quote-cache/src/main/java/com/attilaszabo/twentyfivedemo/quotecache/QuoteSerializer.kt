package com.attilaszabo.twentyfivedemo.quotecache

import androidx.datastore.core.Serializer
import java.io.InputStream
import java.io.OutputStream

object QuoteSerializer : Serializer<QuoteProto> {
    override val defaultValue: QuoteProto = QuoteProto.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): QuoteProto {
        return QuoteProto.parseFrom(input)
    }

    override suspend fun writeTo(t: QuoteProto, output: OutputStream) {
        t.writeTo(output)
    }
}
