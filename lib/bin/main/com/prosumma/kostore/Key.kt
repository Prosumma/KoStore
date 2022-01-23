package com.prosumma.kostore

class Key internal constructor(internal val names: List<Name>) {
    init {
        if (names.isEmpty())
            throw IllegalArgumentException("Empty key is not permitted.")
    }

    constructor(name: Name): this(listOf(name))

    operator fun plus(name: Name): Key =
        Key(names + name)

    operator fun plus(name: String): Key = plus(Name(name))

    override fun toString(): String = names.joinToString("/") { it.toString() }

    override fun equals(other: Any?): Boolean =
        other != null && other is Key && other.names == names

    override fun hashCode(): Int =
        names.hashCode()
}