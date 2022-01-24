package com.prosumma.kostore

interface Store {
    operator fun get(key: Key): String?
    operator fun set(key: Key, json: String?)
    fun contains(key: Key): Boolean =
        this[key] != null
}