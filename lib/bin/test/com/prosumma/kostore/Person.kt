package com.prosumma.kostore

import kotlinx.serialization.Serializable

@Serializable
data class Person(val firstName: String, val lastName: String, val age: Int)