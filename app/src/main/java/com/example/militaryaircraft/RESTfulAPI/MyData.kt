package com.example.militaryaircraft.RESTfulAPI

data class MyData(
    val limit: Int,
    val quotes: List<Quote>,
    val skip: Int,
    val total: Int
)