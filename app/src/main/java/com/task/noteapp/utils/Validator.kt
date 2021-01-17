package com.task.noteapp.utils

import android.widget.TextView
import com.task.noteapp.extensions.validate

fun validateTextLayouts(vararg textLayouts: TextView): Boolean {
    clearTextLayoutError(*textLayouts)
    for (textLayout in textLayouts) {
        if (!textLayout.validate()) return false
    }
    return true
}

fun clearTextLayoutError(vararg textLayouts: TextView) {
    for (textLayout in textLayouts) textLayout.error = null
}
