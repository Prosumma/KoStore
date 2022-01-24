package com.prosumma.kostore

import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString

interface Container {
    fun <T> deserialize(key: Key, deserialize: (String) -> T): T?
    fun <T> serialize(key: Key, value: T, serialize: (T) -> String)
    fun contains(key: Key): Boolean
}

inline operator fun <reified T> Container.get(key: Key): T? =
    deserialize(key, Json::decodeFromString)

inline operator fun <reified T> Container.get(key: Key, noinline default: () -> T): T =
    deserialize(key, Json::decodeFromString) ?: run {
        synchronized(this) {
            val value = default()
            this[key] = value
            value
        }
    }

inline operator fun <reified T> Container.set(key: Key, value: T) =
    serialize(key, value, Json::encodeToString)