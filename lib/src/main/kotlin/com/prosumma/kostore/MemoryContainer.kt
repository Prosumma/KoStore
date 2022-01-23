package com.prosumma.kostore

/**
 * A [Container] which keeps its contents in memory.
 *
 * This is mostly suitable only for testing.
 */
class MemoryContainer(internal val store: MutableMap<String, String> = mutableMapOf()): Container {
    override fun contains(key: Key): Boolean =
        store.keys.contains(key.toString())
    override fun getJSON(key: Key): String? = store[key.toString()]
    override fun setJSON(key: Key, json: String?) {
        val skey = key.toString()
        if (json == null) {
            store.remove(skey)
        } else {
            store[skey] = json
        }
    }
}