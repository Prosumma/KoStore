package com.prosumma.kostore

abstract class ChildDomain<ParentDomain: Domain>(name: Name, parent: ParentDomain):
    Domain(name, parent)
{
    constructor(name: String, parent: ParentDomain): this(Name(name), parent)

    init {
        if (name.name == "")
            throw IllegalArgumentException("A child domain must not have an empty name.")
    }

    override val container: Container
        get() = parent!!.container
}