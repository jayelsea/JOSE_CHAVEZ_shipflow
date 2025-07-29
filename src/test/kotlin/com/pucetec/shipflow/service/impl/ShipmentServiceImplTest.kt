package com.pucetec.shipflow.service.impl

import com.pucetec.shipflow.dto.ShipmentRequest
import com.pucetec.shipflow.dto.ShipmentStatusUpdate
import com.pucetec.shipflow.entity.Shipment
import com.pucetec.shipflow.entity.ShipmentStatusHistory
import com.pucetec.shipflow.entity.Status
import com.pucetec.shipflow.repository.ShipmentRepository
import com.pucetec.shipflow.repository.ShipmentStatusHistoryRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.*

import java.util.*

class ShipmentServiceImplTest {

    private val shipmentRepository: ShipmentRepository = mock()
    private val shipmentStatusHistoryRepository: ShipmentStatusHistoryRepository = mock()

    private val shipmentService = ShipmentServiceImpl(shipmentRepository, shipmentStatusHistoryRepository)

    @Test
    fun `createShipment should save shipment and history`() {
        val request = ShipmentRequest("SMALL_BOX", "Paquete urgente", 2.5, "Quito", "Guayaquil")
        val shipment = Shipment(
            packageType = request.packageType,
            description = request.description,
            weight = request.weight,
            originCity = request.originCity,
            destinationCity = request.destinationCity
        )

        whenever(shipmentRepository.save(any())).thenReturn(shipment)

        val result = shipmentService.createShipment(request)

        verify(shipmentRepository).save(any())
        verify(shipmentStatusHistoryRepository).save(any())
        assertEquals("Guayaquil", result.destinationCity)
    }

    @Test
    fun `createShipment should throw if origin and destination cities are equal`() {
        val request = ShipmentRequest("DOCUMENT", "Papeles legales", 1.0, "Quito", "Quito")

        val exception = assertThrows<IllegalArgumentException> {
            shipmentService.createShipment(request)
        }

        assertEquals("La ciudad de origen y destino no pueden ser iguales", exception.message)
    }

    @Test
    fun `getAllShipments should return all shipments`() {
        val shipments = listOf(Shipment(trackingId = "ABC123"))
        whenever(shipmentRepository.findAll()).thenReturn(shipments)

        val result = shipmentService.getAllShipments()

        assertEquals(1, result.size)
        assertEquals("ABC123", result[0].trackingId)
    }

    @Test
    fun `updateStatus should update shipment status and save history`() {
        val shipment = Shipment(trackingId = "XYZ123", status = Status.IN_TRANSIT)
        val update = ShipmentStatusUpdate(Status.DELIVERED, "Entregado")

        whenever(shipmentRepository.findById("XYZ123")).thenReturn(Optional.of(shipment))
        whenever(shipmentRepository.save(any())).thenReturn(shipment)

        val result = shipmentService.updateStatus("XYZ123", update)

        assertEquals(Status.DELIVERED, result.status)
        verify(shipmentStatusHistoryRepository).save(any())
    }

    @Test
    fun `updateStatus should throw if shipment not found`() {
        whenever(shipmentRepository.findById(any())).thenReturn(Optional.empty())

        val exception = assertThrows<IllegalArgumentException> {
            shipmentService.updateStatus("X", ShipmentStatusUpdate(Status.ON_HOLD))
        }

        assertEquals("Envío no encontrado", exception.message)
    }

    @Test
    fun `updateStatus should throw if already delivered`() {
        val shipment = Shipment(trackingId = "XYZ", status = Status.DELIVERED)
        whenever(shipmentRepository.findById("XYZ")).thenReturn(Optional.of(shipment))

        val exception = assertThrows<IllegalStateException> {
            shipmentService.updateStatus("XYZ", ShipmentStatusUpdate(Status.CANCELLED))
        }

        assertEquals("El envío ya ha sido entregado", exception.message)
    }

    @Test
    fun `updateStatus should throw if status is DELIVERED but current status is not IN_TRANSIT`() {
        val shipment = Shipment(trackingId = "ABC", status = Status.PENDING)
        whenever(shipmentRepository.findById("ABC")).thenReturn(Optional.of(shipment))

        val exception = assertThrows<IllegalStateException> {
            shipmentService.updateStatus("ABC", ShipmentStatusUpdate(Status.DELIVERED))
        }

        assertEquals("Solo se puede marcar como DELIVERED si está en IN_TRANSIT", exception.message)
    }
}
