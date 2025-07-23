package com.pucetec.shipflow.repository

import com.pucetec.shipflow.entity.StatusHistory
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface StatusHistoryRepository : JpaRepository<StatusHistory, UUID> {
    fun findAllByShipment_TrackingId(trackingId: String): List<StatusHistory>
}
