package com.pucetec.shipflow.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.pucetec.shipflow.dto.ShipmentRequest
import com.pucetec.shipflow.dto.ShipmentStatusUpdate
import com.pucetec.shipflow.entity.Shipment
import com.pucetec.shipflow.entity.ShipmentStatusHistory
import com.pucetec.shipflow.entity.Status
import com.pucetec.shipflow.service.ShipmentService
import com.pucetec.shipflow.repository.ShipmentStatusHistoryRepository
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(ShipmentController::class)
class ShipmentControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var shipmentService: ShipmentService

    @MockBean
    lateinit var shipmentStatusHistoryRepository: ShipmentStatusHistoryRepository

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    fun `createShipment should return created shipment`() {
        val request = ShipmentRequest("SMALL_BOX", "Caja con libros", 3.5, "Quito", "Cuenca")
        val shipment = Shipment(trackingId = "TRACK123")

        given(shipmentService.createShipment(request)).willReturn(shipment)

        mockMvc.perform(
            post("/shipments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.trackingId").value("TRACK123"))
    }

    @Test
    fun `getAllShipments should return list of shipments`() {
        val shipments = listOf(Shipment(trackingId = "ABC123"))
        given(shipmentService.getAllShipments()).willReturn(shipments)

        mockMvc.perform(get("/shipments"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].trackingId").value("ABC123"))
    }

    @Test
    fun `updateStatus should return updated shipment`() {
        val update = ShipmentStatusUpdate(Status.ON_HOLD, "Demora inesperada")
        val shipment = Shipment(trackingId = "XYZ123", status = Status.ON_HOLD)

        given(shipmentService.updateStatus("XYZ123", update)).willReturn(shipment)

        mockMvc.perform(
            put("/shipments/XYZ123/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(update))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.status").value("ON_HOLD"))
    }

    @Test
    fun `getShipmentHistory should return status history list`() {
        val history = listOf(
            ShipmentStatusHistory(
                id = 1L,
                shipment = Shipment(trackingId = "ABC123"),
                status = Status.IN_TRANSIT
            )
        )
        given(shipmentStatusHistoryRepository.findByShipmentTrackingId("ABC123")).willReturn(history)

        mockMvc.perform(get("/shipments/ABC123/history"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].status").value("IN_TRANSIT"))
    }
}
