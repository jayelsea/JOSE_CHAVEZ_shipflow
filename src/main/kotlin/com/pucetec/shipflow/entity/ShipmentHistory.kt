package com.pucetec.shipflow.entity

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "shipment_history")
data class ShipmentHistory(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @ManyToOne
    @JoinColumn(name = "shipment_id", nullable = false)
    val shipment: Shipment,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val status: Status,

    @Column(nullable = false)
    val changedAt: LocalDateTime = LocalDateTime.now()
)
