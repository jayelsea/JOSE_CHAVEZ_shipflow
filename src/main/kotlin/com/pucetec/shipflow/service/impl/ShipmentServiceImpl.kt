package com.pucetec.shipflow.service.impl

import com.pucetec.shipflow.dto.ShipmentRequest
import com.pucetec.shipflow.dto.ShipmentStatusUpdate
import com.pucetec.shipflow.entity.Shipment
import com.pucetec.shipflow.entity.ShipmentStatusHistory
import com.pucetec.shipflow.entity.Status
import com.pucetec.shipflow.repository.ShipmentRepository
import com.pucetec.shipflow.repository.ShipmentStatusHistoryRepository
import com.pucetec.shipflow.service.ShipmentService
import org.springframework.stereotype.Service

@Service
class ShipmentServiceImpl(
    private val shipmentRepository: ShipmentRepository,
    private val shipmentStatusHistoryRepository: ShipmentStatusHistoryRepository
) : ShipmentService {

    override fun createShipment(request: ShipmentRequest): Shipment {
        if (request.originCity == request.destinationCity) {
            throw IllegalArgumentException("La ciudad de origen y destino no pueden ser iguales")
        }

        val shipment = Shipment(
            packageType = request.packageType,
            description = request.description,
            weight = request.weight,
            originCity = request.originCity,
            destinationCity = request.destinationCity
        )

        val savedShipment = shipmentRepository.save(shipment)

        val history = ShipmentStatusHistory(
            shipment = savedShipment,
            status = savedShipment.status
        )
        shipmentStatusHistoryRepository.save(history)

        return savedShipment
    }

    override fun getAllShipments(): List<Shipment> {
        return shipmentRepository.findAll()
    }

    override fun updateStatus(trackingId: String, update: ShipmentStatusUpdate): Shipment {
        val shipment = shipmentRepository.findById(trackingId)
            .orElseThrow { IllegalArgumentException("Envío no encontrado") }

        if (shipment.status == Status.DELIVERED) {
            throw IllegalStateException("El envío ya ha sido entregado")
        }

        if (update.newStatus == Status.DELIVERED && shipment.status != Status.IN_TRANSIT) {
            throw IllegalStateException("Solo se puede marcar como DELIVERED si está en IN_TRANSIT")
        }

        shipment.status = update.newStatus
        val updatedShipment = shipmentRepository.save(shipment)

        val history = ShipmentStatusHistory(
            shipment = updatedShipment,
            status = updatedShipment.status
        )
        shipmentStatusHistoryRepository.save(history)

        return updatedShipment
    }
}
