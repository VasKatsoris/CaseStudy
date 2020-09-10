package com.demo.project.ui.forecast

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.demo.project.di.SchedulersModule
import com.demo.project.network.RepositoryInterface
import com.demo.project.network.responsedaos.ForecastResponseDao
import com.demo.project.network.transformers.ForecastToRowsTransformer
import com.demo.project.ui.base.BaseViewModel
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import java.util.*
import kotlin.collections.ArrayList

class ForecastViewModel @ViewModelInject constructor(@SchedulersModule.IoScheduler private val ioScheduler: Scheduler,
                                                     @SchedulersModule.MainThreadScheduler private val mainThreadScheduler: Scheduler,
                                                     private val repository: RepositoryInterface): BaseViewModel(){

    //expose LiveData to fragment instead on MutableLiveData
    //so that the fragment cannot change it's value
    private var rowsMutableLiveData:MutableLiveData<ArrayList<Row>> = MutableLiveData()
    val rowsLiveData: LiveData<ArrayList<Row>> = rowsMutableLiveData
    private val transformer = ForecastToRowsTransformer()
    private var subscription: Disposable? = null

    override fun onCleared() {
        super.onCleared()
        subscription?.dispose()
    }

    fun getForecast(){

        subscription?.dispose()
        subscription = repository.getForecastForStop(getStopNameBasedOnTime())
            .subscribeOn(ioScheduler)
            .observeOn(mainThreadScheduler)
            .doOnSubscribe { setApiCallActive(true) }
            .subscribe(
                { result -> onApiCallResult(result) },
                { onApiCallResult(null) }
            )
    }


    private fun onApiCallResult(result: ForecastResponseDao?){
        setApiCallActive(false)
        subscription?.dispose()
        if(result!=null) {
            rowsMutableLiveData.value = transformer.transform(result)
        }
        else{
            rowsMutableLiveData.value = null
        }
    }


    private fun getStopNameBasedOnTime():String {
        val c: Calendar = Calendar.getInstance()
        val hour: Int = c.get(Calendar.HOUR_OF_DAY)
        val minutes = c.get(Calendar.MINUTE)
        //00:00 – 12:00 -> Marlborough
        //12:01 – 23:59 -> Stillorgan
        return if(hour in 0..11 || hour == 12 && minutes==0){
            "mar"
        } else{
            "sti"
        }
    }

}