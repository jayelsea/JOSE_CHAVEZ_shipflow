package com.pucetec.shipflow.repository

import com.pucetec.shipflow.entity.ShipmentStatusHistory
import org.springframework.data.jpa.repository.JpaRepository

interface ShipmentStatusHistoryRepository : JpaRepository<ShipmentStatusHistory, Long> {
    fun findByShipmentTrackingId(trackingId: String): List<ShipmentStatusHistory>
}
