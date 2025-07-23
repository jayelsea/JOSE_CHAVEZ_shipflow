package com.pucetec.shipflow.entity

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "status_history")
data class StatusHistory(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val status: Status,

    @Column(nullable = false)
    val changedAt: LocalDateTime = LocalDateTime.now(),

    @ManyToOne
    @JoinColumn(name = "shipment_id", nullable = false)
    val shipment: Shipment
)
