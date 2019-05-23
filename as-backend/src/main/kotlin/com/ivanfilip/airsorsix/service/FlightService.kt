package com.ivanfilip.airsorsix.service

import com.ivanfilip.airsorsix.domain.Flight
import com.ivanfilip.airsorsix.domain.Location
import com.ivanfilip.airsorsix.repository.FlightRepository
import com.ivanfilip.airsorsix.repository.LocationRepository
import com.ivanfilip.airsorsix.repository.PlaneRepository
import com.ivanfilip.airsorsix.utills.loggerFor
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import javax.transaction.Transactional
import java.time.format.DateTimeFormatter



@Service
class FlightService(val flightRepository: FlightRepository,
                    val locationRepository: LocationRepository,
                    val planeRepository: PlaneRepository) {

    val logger = loggerFor<FlightService>()

    fun getDepartureLocations(origin: String? = ""): List<Location>?{
        //val locations: List<Location>? = flightRepository.findAllDistinctDepartureLocations()
        //return locations?.filter { it.city.startsWith(origin ?: return locations, ignoreCase = true) }
        logger.info("Getting departure locations by query [{}]", origin)
        return flightRepository
                .findAllDistinctDepartureLocations()
                ?.filter { it.city.startsWith(origin ?: "", ignoreCase = true) }
    }

    fun getArrivalLocations(origin: String, destination: String?): List<Location>?{
        /*val locations: List<Location>? = flightRepository.findAllDistinctArrivalLocations(locationRepository.findLocationByAirport(origin))
        return locations?.filter { it.city.startsWith(destination ?: return locations, ignoreCase = true) }*/
        logger.info("Getting arrival locations by origin [{}] and query [{}]", origin, destination)
        return flightRepository
                .findAllDistinctArrivalLocations(locationRepository.findLocationByAirport(origin))
                ?.filter { it.city.startsWith(destination ?: "", ignoreCase = true) }
    }

    fun getFlightsByLocation(origin: String, destination: String): List<Flight>?{
        logger.info("Getting flights from origin [{}] and destination [{}]", origin, destination)
        return flightRepository
                .findAllByDepartureLocationAndArrivalLocation(locationRepository.findLocationByAirport(origin),
                        locationRepository.findLocationByAirport(destination))
    }

    fun getFlightById(id: String): Flight? {
        logger.info("Get flight with id [{}]", id)
        return flightRepository.findFlightById(id)
    }

    @Transactional
    fun addNewFlight(planeId: String, code: String, departureDateTime: String,
                     arrivalDateTime: String, departureLocationId: String,
                     arrivalLocationId: String): List<Flight>? {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val flights: MutableList<Flight> = mutableListOf()
        for(i in 0..100) {
            val flight = Flight(plane = planeRepository.findPlaneById(planeId) ?: return null,
                    code = code,
                    departureDateTime = LocalDateTime.parse(departureDateTime, formatter).plusWeeks(i.toLong()),
                    arrivalDateTime = LocalDateTime.parse(arrivalDateTime, formatter).plusWeeks(i.toLong()),
                    departureLocation = locationRepository.findLocationById(departureLocationId) ?: return null,
                    arrivalLocation = locationRepository.findLocationById(arrivalLocationId) ?: return null)
            flights.add(flight)
            logger.info("Saving flight [{}]", flight)
            flightRepository.save(flight)

            val returnFlight = Flight(plane = planeRepository.findPlaneById(planeId) ?: return null,
                    code = code,
                    departureDateTime = LocalDateTime.parse(departureDateTime, formatter)
                            .plusWeeks(i.toLong()).plusDays(1.toLong()),
                    arrivalDateTime = LocalDateTime.parse(arrivalDateTime, formatter)
                            .plusWeeks(i.toLong()).plusDays(1.toLong()),
                    departureLocation = locationRepository.findLocationById(arrivalLocationId) ?: return null,
                    arrivalLocation = locationRepository.findLocationById(departureLocationId) ?: return null)
            flights.add(returnFlight)
            logger.info("Saving flight [{}]", returnFlight)
            flightRepository.save(returnFlight)
        }
        return flights
    }
}
