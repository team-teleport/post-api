package com.sooni.postapi.domain.directions

data class Directions(
    val geocoded_waypoints: List<GeocodedWaypoint>,
    val routes: List<Route>,
    val status: String
)