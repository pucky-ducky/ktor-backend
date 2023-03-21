package com.example.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.*

@Serializable
data class Customer(val id: String, val firstName: String, val lastName: String, val email: String)

val customerStorage = mutableListOf<Customer>()

object Customers : Table() {
    val id = varchar("id", 255)
    val firstName = varchar("firstName", 255)
    val lastName = varchar("lastName", 255)
    val email = varchar("email", 255)

    override val primaryKey = PrimaryKey(id)
}