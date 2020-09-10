package com.demo.project.network.transformers

import com.demo.project.network.responsedaos.ForecastResponseDao
import com.demo.project.ui.forecast.Row

class ForecastToRowsTransformer() : Transformer<ForecastResponseDao, ArrayList<Row>>() {

    override fun transform(responseDao: ForecastResponseDao): ArrayList<Row> {
        val rows : ArrayList<Row> = arrayListOf()
        if(responseDao.message?.isNotEmpty() == true) {
            rows.add(Row("\n"+responseDao.message+"\n", null, Row.TYPE_TRAM_FORECAST_ROW))
        }
        responseDao.forecastResponseDirectionDaos?.forEach {direction ->
            rows.add(Row(direction.name, null, Row.TYPE_TITLE))
            direction.trams?.forEach { tram->
                rows.add(Row(tram.destination, tram.dueMins, Row.TYPE_TRAM_FORECAST_ROW))
            }
        }
        return rows
    }

}