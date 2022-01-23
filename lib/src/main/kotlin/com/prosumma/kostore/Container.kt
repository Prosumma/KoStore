package com.prosumma.kostore

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

interface Container {
    fun getJSON(key: Key): String?
    fun setJSON(key: Key, json: String?)
    fun contains(key: Key): Boolean =
        getJSON(key) != null
}

inline operator fun <reified T> Container.get(key: Key): T =
    Json.decodeFromString(getJSON(key)!!)

inline operator fun <reified T> Container.set(key: Key, value: T) =
    setJSON(key, Json.encodeToString(value))