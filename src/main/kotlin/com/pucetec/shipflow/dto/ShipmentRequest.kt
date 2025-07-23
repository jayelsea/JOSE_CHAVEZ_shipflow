package com.pucetec.shipflow.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size

data class ShipmentRequest(
    @field:NotBlank val packageType: String,
    @field:NotBlank @field:Size(max = 50) val description: String,
    @field:Positive val weight: Double,
    @field:NotBlank val originCity: String,
    @field:NotBlank val destinationCity: String
)
