package com.example.militaryaircraft.RESTfulAPI

import com.example.militaryaircraft.RESTfulAPI.MyData
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("quotes")
    fun getQuotes() : Call<MyData>
}