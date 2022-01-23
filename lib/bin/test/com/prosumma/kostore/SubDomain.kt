package com.prosumma.kostore

class SubDomain(parent: AppDomain): ChildDomain<AppDomain>(NAME, parent) {
    companion object {
        const val NAME = "sub"
    }

    var fish: Int by stored { 0 }
    var person: Person? by stored()
    var bob: Person by stored { Person("Bob", "Smith", 44) }
}

val AppDomain.sub: SubDomain by child(SubDomain.NAME, ::SubDomain)