package com.prosumma.kostore

/**
 * A [Store] which keeps its contents in memory.
 *
 * This is mostly suitable only for testing.
 */
class MemoryStore(internal val store: MutableMap<String, String> = mutableMapOf()): Store {
    override fun contains(key: Key): Boolean =
        store.keys.contains(key.toString())
    override operator fun get(key: Key): String? = store[key.toString()]
    override operator fun set(key: Key, json: String?) {
        val skey = key.toString()
        if (json == null) {
            store.remove(skey)
        } else {
            store[skey] = json
        }
    }
}