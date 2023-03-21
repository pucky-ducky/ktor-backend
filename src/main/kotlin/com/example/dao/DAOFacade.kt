package com.example.dao

import com.example.models.*
import java.time.LocalDate

interface DAOFacade {
    suspend fun allCustomers(): List<Customer>
    suspend fun customer(id: String): Customer?
    suspend fun addNewCustomer(id: String, firstName: String, lastName: String, email: String): Customer?
    suspend fun editCustomer(id: String, firstName: String, lastName: String, email: String): Boolean
    suspend fun deleteCustomer(id: String): Boolean

    suspend fun allEquipment(): List<Equipment>
    suspend fun equipment(id: Int): Equipment?
    suspend fun addNewEquipment(
        name: String,
        equipmentType: String,
        manufacturer: String,
        model: String,
        serialNumber: String,
        location: String,
        dateInstalled: LocalDate,
        lastMaintenanceData: LocalDate,
        ): Equipment?
    suspend fun editEquipment(
        id: Int,
        name: String,
        equipmentType: String,
        manufacturer: String,
        model: String,
        serialNumber: String,
        location: String,
        dateInstalled: LocalDate,
        lastMaintenanceData: LocalDate,
    ): Boolean
    suspend fun deleteEquipment(id: Int): Boolean
}