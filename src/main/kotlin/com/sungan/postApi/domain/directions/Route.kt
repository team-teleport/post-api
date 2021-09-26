package com.sungan.postApi.domain.directions

data class Route(
    val legs: List<Leg>,
    val summary: String
)