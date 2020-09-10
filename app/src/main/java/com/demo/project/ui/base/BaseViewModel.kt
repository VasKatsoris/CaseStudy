package com.demo.project.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel: ViewModel(){

    private val isApiCallActive: MutableLiveData<Boolean> = MutableLiveData()

    open fun isLoading(): LiveData<Boolean> {
        return isApiCallActive
    }

    fun setApiCallActive(apiCallActive: Boolean){
        if(isApiCallActive.value!=apiCallActive) {
            isApiCallActive.value = apiCallActive
        }
    }


}