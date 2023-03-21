package com.example.plugins

import com.example.routes.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import com.example.dao.*
import com.example.models.orderStorage
import io.ktor.server.request.*
import io.ktor.server.util.*


fun Application.configureRouting() {
    routing {
        customerRouting()
        listOrdersRoute()
        getOrderRoute()
        totalizeOrderRoute()
        get {
            call.respond(mapOf("equipment_table" to dao.allEquipment()))
        }
    }
}
