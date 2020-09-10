package com.demo.project.network

import com.demo.project.network.responsedaos.ForecastResponseDao
import io.reactivex.Single
import retrofit2.Call
import javax.inject.Inject

class RepositoryImpl @Inject constructor (private val api: ApiCalls):
    RepositoryInterface {

    override fun getForecastForStop(stop: String): Single<ForecastResponseDao> {
        return api.getForecastForStop(stop)
    }
}