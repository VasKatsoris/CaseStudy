package com.demo.project.ui.forecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demo.project.R
import com.demo.project.utils.Utils
import com.demo.project.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_forecast.*

@AndroidEntryPoint
class ForecastFragment : BaseFragment() {

    private val viewModel: ForecastViewModel by viewModels()

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?  ): View? {
        return inflater.inflate(R.layout.fragment_forecast, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(DividerItemDecoration( context, RecyclerView.VERTICAL))

        viewModel.isLoading().observe(viewLifecycleOwner, Observer { isLoading ->
            progressBar.visibility = if(isLoading) View.VISIBLE else View.GONE
        })

        viewModel.rowsLiveData.observe(viewLifecycleOwner, Observer { rows-> updateUi(rows)  })

        refreshButton.setOnClickListener { getForecast()  }

        if(viewModel.rowsLiveData.value==null) {
            Utils.doOnceOnGlobalLayoutOfView(view, Runnable {
                getForecast()
                Utils.animateViewsEntry(listOf(luasLogoImg, welcomeTv, progressBar, refreshButton))
            })
        }
    }

    private fun getForecast(){
        if(!hasConnection()){
            showConnectionDialog()
            return
        }
        viewModel.getForecast()
    }

    private fun updateUi(rows: ArrayList<Row>?) {
        if(rows == null){
            recyclerView.adapter = RowsAdapter(arrayListOf())
        }
        else if(recyclerView.adapter == null){
            recyclerView.adapter = RowsAdapter(rows)
            Utils.doOnceOnGlobalLayoutOfView(recyclerView, Runnable {
                Utils.animateRecyclerViewNow(recyclerView)
            })
        }
        else {
            (recyclerView.adapter as RowsAdapter).updateData(rows)
        }

        val showWelcomeTv = (recyclerView.adapter as RowsAdapter).itemCount==0
        welcomeTv.visibility = if(showWelcomeTv) View.VISIBLE else View.GONE
    }

}