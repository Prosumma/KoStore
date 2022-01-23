package com.prosumma.kostore

data class Name(internal val name: String) {
    init {
        if (name.contains('/') || (name.isNotEmpty() && name.isBlank()))
            throw IllegalArgumentException(
                "A name may include any character except slash (/) and empty whitespace."
            )
    }

    operator fun plus(name: Name): Key =
        Key(listOf(this, name))

    override fun toString(): String = name

    override fun equals(other: Any?): Boolean =
        other != null && other is Name && other.name == name

    override fun hashCode(): Int =
        name.hashCode()
}
