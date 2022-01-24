package com.prosumma.kostore

import kotlin.reflect.KProperty

class ChildDelegate<P: Domain, D: Domain>(
    private val name: Name? = null,
    private val create: (P) -> D
) {
    operator fun getValue(parent: Domain, property: KProperty<*>): D =
        parent.getChild(name ?: Name(property.name), create)
}

fun <P: Domain, D: Domain> child(name: Name?, create: (P) -> D): ChildDelegate<P, D> =
    ChildDelegate(name, create)

fun <P: Domain, D: Domain> child(create: (P) -> D): ChildDelegate<P, D> =
    ChildDelegate(null, create)

fun <P: Domain, D: Domain> child(name: String, create: (P) -> D): ChildDelegate<P, D> =
    ChildDelegate(Name(name), create)