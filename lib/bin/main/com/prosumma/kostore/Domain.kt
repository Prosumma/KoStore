package com.prosumma.kostore

import kotlin.reflect.KProperty

abstract class Domain(val name: Name, val parent: Domain? = null) {
    constructor(name: String, parent: Domain? = null): this(Name(name), parent)

    internal val _children: MutableMap<Name, Domain> = mutableMapOf()
    val children: MutableMap<Name, Domain> get() = _children

    val key: Key
        get() = parent?.let { it.key + name } ?: Key(name)

    abstract val container: Container

    override fun equals(other: Any?): Boolean =
        other != null && other is Domain && key == other.key

    override fun hashCode(): Int =
        key.hashCode()
}

class ChildDelegate<P: Domain, D: Domain>(val name: Name, val create: (P) -> D) {
    @Suppress("UNCHECKED_CAST")
    operator fun getValue(parent: P, property: KProperty<*>): D =
        parent._children.getOrPut(name) { create(parent) } as D
}

fun <P: Domain, D: Domain> child(name: Name, create: (P) -> D): ChildDelegate<P, D> =
    ChildDelegate(name, create)

fun <P: Domain, D: Domain> child(name: String, create: (P) -> D): ChildDelegate<P, D> =
    ChildDelegate(Name(name), create)