package com.demo.project.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.demo.project.R
import kotlin.math.min


@BindingAdapter("tramMinutesLeft")
fun tramMinutesLeft(textView: TextView, text: String?) {
    //handle case where the tram is DUE and the text is not numeric
    textView.text = if(text?.toIntOrNull() != null) textView.resources.getString(R.string.min, text) else text
    textView.visibility = if(text !=null && text.isNotEmpty()) View.VISIBLE else View.GONE
}
