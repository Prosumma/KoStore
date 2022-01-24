package com.prosumma.kostore

import kotlin.reflect.KProperty

typealias PropertyGetter<T> = (Key, Container) -> T
typealias PropertySetter<T> = (Key, Container, T) -> Unit

class StoredDelegate<D: Domain, T>(
    private val name: Name? = null,
    private val getter: PropertyGetter<T>,
    private val setter: PropertySetter<T>
) {
    operator fun getValue(domain: D, property: KProperty<*>): T =
        getter(domain.key + (name ?: Name(property.name)), domain.container)

    operator fun setValue(domain: D, property: KProperty<*>, value: T) =
        setter(domain.key + (name ?: Name(property.name)), domain.container, value)
}

inline fun <D: Domain, reified T> stored(name: Name?, noinline default: () -> T):
    StoredDelegate<D, T>
{
    val getter: PropertyGetter<T> = { key, container ->
        container[key, default]
    }
    val setter: PropertySetter<T> = { key, container, value ->
        container[key] = value
    }
    return StoredDelegate(name, getter, setter)
}

inline fun <D: Domain, reified T> stored(name: Name? = null): StoredDelegate<D, T?> =
    stored(name) { null }

inline fun <D: Domain, reified T> stored(noinline default: () -> T): StoredDelegate<D, T> =
    stored(null, default)

inline fun <D: Domain, reified T> stored(
    name: String,
    noinline default: () -> T
): StoredDelegate<D, T> = stored(Name(name), default)

inline fun <D: Domain, reified T> stored(name: String): StoredDelegate<D, T?> =
    stored(Name(name))