package com.example.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.date
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Serializer(forClass = LocalDate::class)
object LocalDateSerializer : KSerializer<LocalDate> {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE

    override val descriptor = PrimitiveSerialDescriptor("LocalDate", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: LocalDate) {
        encoder.encodeString(value.format(formatter))
    }

    override fun deserialize(decoder: Decoder): LocalDate {
        return LocalDate.parse(decoder.decodeString(), formatter)
    }
}

@Serializable
data class Equipment(
    val id: Int,
    val name: String,
    val equipmentType: String,
    val manufacturer: String,
    val model: String,
    val serialNumber: String,
    val location: String,
    @Serializable(with = LocalDateSerializer::class)
    val dateInstalled: LocalDate, // Consider using a proper date type, like java.time.LocalDate or java.time.LocalDateTime
    @Serializable(with = LocalDateSerializer::class)
    val lastMaintenanceDate: LocalDate? // Nullable, same as above
)

val equipmentStorage = mutableListOf<Equipment>()

object EquipmentTable : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 255)
    val equipmentType = varchar("equipment_type", 255)
    val manufacturer = varchar("manufacturer", 255)
    val model = varchar("model", 255)
    val serialNumber = varchar("serial_number", 255)
    val location = varchar("location", 255)
    val dateInstalled = date("date_installed")
    val lastMaintenanceDate = date("last_maintenance_date").nullable()

    override val primaryKey = PrimaryKey(id)
}