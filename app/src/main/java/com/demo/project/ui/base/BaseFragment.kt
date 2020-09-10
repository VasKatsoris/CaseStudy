package com.demo.project.ui.base

import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.demo.project.R
import com.demo.project.utils.Utils

open class BaseFragment :Fragment(){

    fun hasConnection(): Boolean {
        return context?.let { Utils.isConnected(it) }?:true
    }

    fun showConnectionDialog(){
        context?.let {
            AlertDialog.Builder(it)
                .setMessage(getString(R.string.No_connection))
                .setNegativeButton(getString(R.string.Ok)) { dialog, i -> dialog.dismiss() }
                .create()
                .show()
        }
    }

}