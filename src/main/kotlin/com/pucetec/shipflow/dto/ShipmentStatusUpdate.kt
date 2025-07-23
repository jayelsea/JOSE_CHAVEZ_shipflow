package com.pucetec.shipflow.dto

import com.pucetec.shipflow.entity.Status

data class ShipmentStatusUpdate(
    val newStatus: Status,
    val comment: String? = null
)
