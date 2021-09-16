package com.sooni.postapi.domain.directions

data class Route(
    val legs: List<Leg>,
    val summary: String
)