package com.example

import io.ktor.server.application.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*
import io.ktor.http.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.*
import com.example.dao.*


fun main() {
    embeddedServer(Netty, port = 8080, host = "localhost", module = Application::module).start(wait = true)
}

fun Application.module() {
    install(CORS) {
        anyHost()
    }
    DatabaseFactory.init()
    configureRouting()
    configureSerialization()
}