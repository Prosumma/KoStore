package com.prosumma.kostore

class StandardContainer(val store: Store): Container {
    override fun <T> deserialize(key: Key, deserialize: (String) -> T): T? =
        store[key]?.let(deserialize)

    override fun <T> serialize(key: Key, value: T, serialize: (T) -> String) {
        store[key] = serialize(value)
    }

    override fun contains(key: Key): Boolean = store.contains(key)
}