package com.pucetec.shipflow.repository

import com.pucetec.shipflow.entity.Shipment
import org.springframework.data.jpa.repository.JpaRepository

interface ShipmentRepository : JpaRepository<Shipment, String>
