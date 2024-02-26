package com.example.pressurenote.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment

fun View.isVisible(isShowLoading: Boolean, container: View) {
    if (isShowLoading) {
        this.visibility = View.VISIBLE
        container.visibility = View.GONE
    } else {
        this.visibility = View.GONE
        container.visibility = View.VISIBLE
    }
}

fun Context.toast(msg: String, length: Int = Toast.LENGTH_SHORT) {
    return Toast.makeText(this, msg, length).show()
}

fun Fragment.toastFragment(msg: String, length: Int = Toast.LENGTH_SHORT) {
    requireContext().toast(msg, length)
}