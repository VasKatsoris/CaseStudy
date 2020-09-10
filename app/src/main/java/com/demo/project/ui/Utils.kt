package com.demo.project.ui

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.transition.Fade
import android.transition.Slide
import android.transition.Transition
import android.transition.TransitionSet
import android.view.Gravity
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.animation.*
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Utils {


    companion object {

        private fun getRightToLeftAnimation(relativeTo: Int): Animation? {
            return TranslateAnimation(relativeTo, 1f, relativeTo, 0f, relativeTo, 0f, relativeTo, 0f)
        }

        private fun getEntryAnimation(delay: Int): AnimationSet? {
            val set = AnimationSet(true)
            val translateAnim: Animation? = Utils.getRightToLeftAnimation(Animation.RELATIVE_TO_PARENT)
            set.addAnimation(AlphaAnimation(-8f, 1f))
            set.addAnimation(translateAnim)
            set.startOffset = delay.toLong()
            set.duration = 400
            set.interpolator = DecelerateInterpolator(2.5f)
            return set
        }

        fun animateRecyclerViewNow(recyclerView: RecyclerView) {
            val gridLayoutManager = recyclerView.layoutManager as LinearLayoutManager? ?: return
            val visibleChildren =  gridLayoutManager.findLastVisibleItemPosition() - gridLayoutManager.findFirstVisibleItemPosition() + 1
            val views = arrayListOf<View>()
            for (i in 0 until visibleChildren) {
                val v = recyclerView.getChildAt(i) ?: continue
                views.add(v)
            }
            animateViewsEntry(views)
        }

        fun animateViewsEntry(views:List<View>){
            var delay=0
            views.forEach {
                it.startAnimation(Utils.getEntryAnimation(delay))
                delay+=50
            }
        }

        fun doOnceOnGlobalLayoutOfView(  v: View?,  r: Runnable?  ) {
            v?.viewTreeObserver?.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    v.viewTreeObserver?.removeOnGlobalLayoutListener(this)
                    r?.run()
                }
            })
        }

        fun isConnected(context: Context): Boolean {
            var result = 0 // connection type. 0: none; 1: mobile data; 2: wifi
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)
                if (capabilities != null) {
                    result = when {
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> { 2 }
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> { 1 }
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> { 3 }
                        else -> 0
                    }
                }
            } else {
                val activeNetwork = cm.activeNetworkInfo
                if (activeNetwork != null) {
                    // connected to the internet
                    result = when (activeNetwork.type) {
                        ConnectivityManager.TYPE_WIFI -> { 2 }
                        ConnectivityManager.TYPE_MOBILE -> { 1 }
                        ConnectivityManager.TYPE_VPN -> { 3 }
                        else -> 0
                    }
                }
            }
            return result!=0
        }
    }
}