package com.prosumma.kostore

class AppDomain(override val container: Container): Domain("", null) {
    constructor(store: Store): this(StandardContainer(store))
}