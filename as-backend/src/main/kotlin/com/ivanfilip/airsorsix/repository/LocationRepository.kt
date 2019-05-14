package com.ivanfilip.airsorsix.repository

import com.ivanfilip.airsorsix.domain.Location
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LocationRepository : JpaRepository<Location, String> {
}