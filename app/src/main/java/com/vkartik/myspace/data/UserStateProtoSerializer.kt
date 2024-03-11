package com.vkartik.myspace.data

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import com.vkartik.myspace.UserStateProtoOuterClass.UserStateProto

object UserStateProtoSerializer : Serializer<UserStateProto> {
    override val defaultValue: UserStateProto
        get() = UserStateProto.getDefaultInstance()

    override suspend fun readFrom(input: java.io.InputStream): UserStateProto {
        try {
            return UserStateProto.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: UserStateProto, output: java.io.OutputStream) {
        t.writeTo(output)
    }
}