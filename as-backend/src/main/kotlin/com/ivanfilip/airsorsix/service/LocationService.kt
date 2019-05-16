package com.ivanfilip.airsorsix.service

import com.ivanfilip.airsorsix.domain.Location
import com.ivanfilip.airsorsix.repository.LocationRepository
import com.ivanfilip.airsorsix.utills.loggerFor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LocationService(val repository: LocationRepository) {

    val logger = loggerFor<LocationService>()

    @Transactional
    fun addNewLocation(city: String, country: String, airport: String, price: Int): Location {
        val location = Location(city = city, country = country,
                airport = airport, price = price)
        logger.info("Saving location [{}]", location)
        repository.save(location)
        return location
    }
}