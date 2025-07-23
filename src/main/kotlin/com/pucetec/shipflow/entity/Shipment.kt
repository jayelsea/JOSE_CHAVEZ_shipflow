package com.pucetec.shipflow.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "shipment")
data class Shipment(
    @Id
    val trackingId: String = "",

    val packageType: String = "",

    val description: String = "",

    val weight: Double = 0.0,

    val originCity: String = "",

    val destinationCity: String = "",

    var status: Status = Status.CREATED,

    val createdAt: LocalDateTime = LocalDateTime.now(),

    @OneToMany(mappedBy = "shipment", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)

    val history: MutableList<StatusHistory> = mutableListOf(),


)

{
    constructor() : this("", "", "", 0.0, "", "", Status.CREATED, LocalDateTime.now())
}
