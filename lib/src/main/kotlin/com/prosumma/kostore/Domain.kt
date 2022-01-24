package com.prosumma.kostore

abstract class Domain(val name: Name, val parent: Domain? = null) {
    constructor(name: String, parent: Domain? = null): this(Name(name), parent)

    private val _children: MutableMap<Name, Domain> = mutableMapOf()
    val children: MutableMap<Name, Domain> get() = _children

    val key: Key
        get() = parent?.let { it.key + name } ?: Key(name)

    abstract val container: Container

    @Suppress("UNCHECKED_CAST")
    fun <Parent: Domain, Child: Domain> getChild(name: Name, default: (Parent) -> Child): Child =
        _children.getOrPut(name) {
            val child = default(this as Parent)
            if (child.parent != this)
                throw IllegalArgumentException("Mismatched parents.")
            if (child.name != name)
                throw IllegalArgumentException("Mismatched names ($name and ${child.name}) for domain.")
            child
        } as Child

    override fun equals(other: Any?): Boolean =
        other != null && other is Domain && key == other.key

    override fun hashCode(): Int =
        key.hashCode()
}