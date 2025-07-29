package com.pucetec.shipflow

import com.pucetec.shipflow.dto.ShipmentRequest
import com.pucetec.shipflow.dto.ShipmentStatusUpdate
import com.pucetec.shipflow.entity.Shipment
import com.pucetec.shipflow.entity.ShipmentStatusHistory
import com.pucetec.shipflow.repository.ShipmentStatusHistoryRepository
import com.pucetec.shipflow.service.ShipmentService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/shipments")
class ShipmentController(
    private val shipmentService: ShipmentService,
    private val shipmentStatusHistoryRepository: ShipmentStatusHistoryRepository
) {

    @PostMapping
    fun createShipment(@RequestBody request: ShipmentRequest): ResponseEntity<Shipment> {
        val shipment = shipmentService.createShipment(request)
        return ResponseEntity.ok(shipment)
    }

    @GetMapping
    fun getAllShipments(): ResponseEntity<List<Shipment>> {
        val shipments = shipmentService.getAllShipments()
        return ResponseEntity.ok(shipments)
    }

    @PutMapping("/{trackingId}/status")
    fun updateStatus(
        @PathVariable trackingId: String,
        @RequestBody update: ShipmentStatusUpdate
    ): ResponseEntity<Shipment> {
        val shipment = shipmentService.updateStatus(trackingId, update)
        return ResponseEntity.ok(shipment)
    }

    @GetMapping("/{trackingId}/history")
    fun getShipmentHistory(@PathVariable trackingId: String): ResponseEntity<List<ShipmentStatusHistory>> {
        val history = shipmentStatusHistoryRepository.findByShipmentTrackingId(trackingId)
        return ResponseEntity.ok(history)
    }
}
