package com.sungan.postApi.domain.directions

data class Leg(
    val bounds: Bounds,
    val copyrights: String,
    val overview_polyline: OverviewPolyline,
    val steps: List<Step>,
    val warnings: List<Any>,
    val waypoint_order: List<Int>
)