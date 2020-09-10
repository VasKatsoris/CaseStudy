package com.demo.project.ui.forecast

class Row(var text1: String?, val text2:String?, val type: Int) {

    companion object{
        const val TYPE_MESSAGE = 1
        const val TYPE_TITLE  = 2
        const val TYPE_TRAM_FORECAST_ROW = 3
    }
}