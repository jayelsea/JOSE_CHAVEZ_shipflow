package com.pucetec.shipflow.repository

import com.pucetec.shipflow.entity.ShipmentHistory
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ShipmentHistoryRepository : JpaRepository<ShipmentHistory, UUID> {
    fun findAllByShipment_TrackingId(trackingId: String): List<ShipmentHistory>
}
