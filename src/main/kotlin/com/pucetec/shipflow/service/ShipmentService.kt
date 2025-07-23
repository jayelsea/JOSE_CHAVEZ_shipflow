package com.pucetec.shipflow.service

import com.pucetec.shipflow.dto.ShipmentRequest
import com.pucetec.shipflow.dto.ShipmentStatusUpdate
import com.pucetec.shipflow.entity.Shipment

interface ShipmentService {
    fun createShipment(request: ShipmentRequest): Shipment
    fun updateStatus(trackingId: String, update: ShipmentStatusUpdate): Shipment
    fun getAllShipments(): List<Shipment>
}
