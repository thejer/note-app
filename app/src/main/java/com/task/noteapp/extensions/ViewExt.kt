package com.task.noteapp.extensions

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.core.view.children

fun View.show() {
    visibility = VISIBLE
}

fun View.hide() {
    visibility = GONE
}

fun ViewGroup.showViewWithChildren() {
    show()
    for (view in children) {
        view.show()
    }
}

inline fun <reified T> ViewGroup.inflate(@LayoutRes layoutRes: Int): T {
    return LayoutInflater.from(context).inflate(layoutRes, this, false) as T
}

fun TextView.underline() {
    paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG
}