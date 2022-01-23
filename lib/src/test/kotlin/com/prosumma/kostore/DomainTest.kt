package com.prosumma.kostore

import kotlin.test.Test
import kotlin.test.assertEquals

class DomainTest {
    @Test
    fun testDomain() {
        val container = MemoryContainer()
        val appDomain = AppDomain(container)
        appDomain.watusi = "watusi"
        assertEquals(appDomain.watusi, "watusi")
        assertEquals(container.store["/watusi"], "\"watusi\"")

        assertEquals(appDomain.sub.fish, 0)
        appDomain.sub.fish = 3
        assertEquals(appDomain.sub.fish, 3)
        assertEquals(container.store["/sub/fish"], "3")

        val person = Person("Joe", "Dirt", 40)
        appDomain.sub.person = person
        assertEquals(appDomain.sub.person, person)
        assertEquals(
            container.store["/sub/person"],
            """{"firstName":"Joe","lastName":"Dirt","age":40}"""
        )

        val bob = Person("Bob", "Smith", 44)
        assertEquals(appDomain.sub.bob, bob)
        assertEquals(
            container.store["/sub/bob"],
            """{"firstName":"Bob","lastName":"Smith","age":44}"""
        )
    }
}