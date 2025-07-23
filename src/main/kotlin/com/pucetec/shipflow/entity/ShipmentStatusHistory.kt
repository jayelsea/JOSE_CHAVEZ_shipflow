package com.pucetec.shipflow.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "shipment_status_history")
data class ShipmentStatusHistory(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipment_tracking_id")
    val shipment: Shipment,

    @Enumerated(EnumType.STRING)
    val status: Status,

    val changedAt: LocalDateTime = LocalDateTime.now()
)
