package com.axlin.demo.network

import com.axlin.demo.models.requests.PersonRequest
import com.axlin.demo.models.responses.PersonResponse
import com.axlin.demo.models.responses.RouteResponse
import com.axlin.demo.models.responses.StationRespose
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface APIInterface {
    @POST("/mobile/login")
    fun login(@Body personRequest: PersonRequest): Call<PersonResponse>

    @GET("/mobile/stations")
    fun getStations(): Call<List<StationRespose>>

    @GET("/mobile/routes")
    fun getRoutes(): Call<List<RouteResponse>>
}