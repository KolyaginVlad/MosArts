package ru.cpc.mosarts.domain.repositories

import ru.cpc.mosarts.domain.models.School

interface SchoolRepository {

    suspend fun getSchools(): Result<List<School>>
}