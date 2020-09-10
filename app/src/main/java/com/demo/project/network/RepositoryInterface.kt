package com.demo.project.network

import com.demo.project.network.responsedaos.ForecastResponseDao
import io.reactivex.Single
import retrofit2.Call

interface RepositoryInterface{
    fun getForecastForStop(s: String): Single<ForecastResponseDao>
}