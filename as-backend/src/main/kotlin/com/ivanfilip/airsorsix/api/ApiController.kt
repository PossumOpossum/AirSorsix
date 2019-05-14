package com.ivanfilip.airsorsix.api

import com.ivanfilip.airsorsix.domain.Flight
import com.ivanfilip.airsorsix.domain.Location
import com.ivanfilip.airsorsix.domain.Plane
import com.ivanfilip.airsorsix.service.FlightService
import com.ivanfilip.airsorsix.service.LocationService
import com.ivanfilip.airsorsix.service.PlaneService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class ApiController(val flightService: FlightService,
                    val locationService: LocationService,
                    val planeService: PlaneService) {

    @PostMapping("/create/location")
    fun createLocation(@RequestBody location: CreateLocationRequest): Location {
        return locationService.addNewLocation(location.city, location.country, location.airport,
                location.price)
    }

    @PostMapping("/create/plane")
    fun createPlane(@RequestBody plane: CreatePlaneRequest): Plane {
        return planeService.addNewPlane(plane.manuufacturer, plane.model, plane.numberOfEconomySeat,
                plane.numberOfBusinessSeat, plane.priceOfEconomySeat, plane.priceOfBusinessSeat)
    }

    @PostMapping("/create/flight")
    fun createPoll(@RequestBody flight: CreateFlightRequest): Flight {
        return flightService.addNewLocation(location.city, location.country, location.airport, location.price)
    }
}