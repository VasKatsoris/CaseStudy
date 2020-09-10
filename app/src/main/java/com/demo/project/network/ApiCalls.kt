package com.demo.project.network

import com.demo.project.network.responsedaos.ForecastResponseDao
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiCalls {

    companion object{
        const val BASE_URL = "https://luasforecasts.rpa.ie/"
    }

    @GET("xml/get.ashx?action=forecast&encrypt=false")
    fun getForecastForStop(@Query("stop") stop:String): Single<ForecastResponseDao>

}