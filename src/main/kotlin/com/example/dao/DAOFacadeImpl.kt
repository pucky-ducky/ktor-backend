package com.example.dao

import com.example.dao.DatabaseFactory.dbQuery
import com.example.models.Customer
import com.example.models.Customers
import com.example.models.Equipment
import com.example.models.EquipmentTable
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.javatime.date
import java.time.LocalDate


class DAOFacadeImpl : DAOFacade {
    private fun resultRowToCustomer(row: ResultRow) = Customer(
        id = row[Customers.id],
        firstName = row[Customers.firstName],
        lastName = row[Customers.lastName],
        email = row[Customers.email]
    )
    private fun resultRowToEquipment(row: ResultRow) = Equipment(
        id = row[EquipmentTable.id],
        name = row[EquipmentTable.name],
        equipmentType = row[EquipmentTable.equipmentType],
        manufacturer = row[EquipmentTable.manufacturer],
        model = row[EquipmentTable.model],
        serialNumber = row[EquipmentTable.serialNumber],
        location = row[EquipmentTable.location],
        dateInstalled = row[EquipmentTable.dateInstalled],
        lastMaintenanceDate = row[EquipmentTable.lastMaintenanceDate]
    )

    override suspend fun allCustomers(): List<Customer> = dbQuery {
        Customers.selectAll().map(::resultRowToCustomer)
    }

    override suspend fun customer(id: String): Customer? = dbQuery {
        Customers
            .select { Customers.id eq id}
            .map(::resultRowToCustomer)
            .singleOrNull()
    }

    override suspend fun addNewCustomer(id: String, firstName: String, lastName: String, email: String): Customer? = dbQuery {
        val insertStatement = Customers.insert {
            it[Customers.id] = id
            it[Customers.firstName] = firstName
            it[Customers.lastName] = lastName
            it[Customers.email] = email
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToCustomer)
    }

    override suspend fun editCustomer(id: String, firstName: String, lastName: String, email: String): Boolean = dbQuery {
        Customers.update({Customers.id eq id}) {
            it[Customers.firstName] = firstName
            it[Customers.lastName] = lastName
            it[Customers.email] = email
        } > 0
    }

    override suspend fun deleteCustomer(id: String): Boolean = dbQuery {
        Customers.deleteWhere { Customers.id eq id } > 0
    }

    override suspend fun allEquipment(): List<Equipment> = dbQuery {
        EquipmentTable.selectAll().map(::resultRowToEquipment)
    }

    override suspend fun equipment(id: Int): Equipment? = dbQuery {
       EquipmentTable
           .select { EquipmentTable.id eq id}
           .map(::resultRowToEquipment)
           .singleOrNull()
    }

    override suspend fun addNewEquipment(
        name: String,
        equipmentType: String,
        manufacturer: String,
        model: String,
        serialNumber: String,
        location: String,
        dateInstalled: LocalDate,
        lastMaintenanceData: LocalDate
    ): Equipment? = dbQuery {
        val insertStatement = EquipmentTable.insert {
            it[EquipmentTable.name] = name
            it[EquipmentTable.equipmentType] = equipmentType
            it[EquipmentTable.manufacturer] = manufacturer
            it[EquipmentTable.model] = model
            it[EquipmentTable.serialNumber] = serialNumber
            it[EquipmentTable.location] = location
            it[EquipmentTable.dateInstalled] = dateInstalled
            it[EquipmentTable.lastMaintenanceDate] = lastMaintenanceData
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToEquipment)
    }

    override suspend fun editEquipment(
        id: Int,
        name: String,
        equipmentType: String,
        manufacturer: String,
        model: String,
        serialNumber: String,
        location: String,
        dateInstalled: LocalDate,
        lastMaintenanceData: LocalDate
    ): Boolean = dbQuery {
        EquipmentTable.update({EquipmentTable.id eq id}) {
            it[EquipmentTable.name] = name
            it[EquipmentTable.equipmentType] = equipmentType
            it[EquipmentTable.manufacturer] = manufacturer
            it[EquipmentTable.model] = model
            it[EquipmentTable.serialNumber] = serialNumber
            it[EquipmentTable.location] = location
            it[EquipmentTable.dateInstalled] = dateInstalled
            it[EquipmentTable.lastMaintenanceDate] = lastMaintenanceData
        } > 0
    }

    override suspend fun deleteEquipment(id: Int): Boolean = dbQuery {
        EquipmentTable.deleteWhere { EquipmentTable.id eq id } > 0
    }
}

val dao: DAOFacade = DAOFacadeImpl().apply {
    runBlocking {
        if(allCustomers().isEmpty()) {
            addNewCustomer("200", "...it's what keeps me going.", "ln", "sam@umass.edu")
        }
        if(allEquipment().isEmpty()) {
            addNewEquipment("name", "type", "man", "model", "serial", "loc", LocalDate.of(2023, 3, 19), LocalDate.of(2023, 3, 20))
        }
    }
}