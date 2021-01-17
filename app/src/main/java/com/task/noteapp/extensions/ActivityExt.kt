package com.task.noteapp.extensions

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.annotation.AttrRes
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.task.noteapp.R

fun Activity.disableTouch() {
    window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
}

fun Activity.enableTouch() {
    window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
}

fun Activity.hideKeyBoard() {
    val view = currentFocus
    if (view != null) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun Activity.viewUrl(url: String) {
    val params = CustomTabColorSchemeParams.Builder()
        .setToolbarColor(resolveColor(R.attr.colorPrimary))
        .build()

    val customTabsIntent = CustomTabsIntent.Builder()
        .setColorSchemeParams(CustomTabsIntent.COLOR_SCHEME_DARK, params)
        .build()
    try {
        customTabsIntent.launchUrl(this, url.toUri())
    } catch (_: ActivityNotFoundException) {
        val chooser = Intent.createChooser(
            Intent(Intent.ACTION_VIEW)
                .setData(url.toUri()), "View URL"
        )
        startActivity(chooser)
    }
}

private fun Activity.resolveColor(@AttrRes attr: Int): Int {
    val a = theme.obtainStyledAttributes(intArrayOf(attr))
    try {
        return a.getColor(0, 0)
    } finally {
        a.recycle()
    }
}

fun Context.showDialog(
    paramsMap: Map<String, String>,
    neutralAction: (dialog: DialogInterface) -> Unit,
    negativeAction: (dialog: DialogInterface) -> Unit,
    positiveAction: (dialog: DialogInterface) -> Unit
) {
    MaterialAlertDialogBuilder(this)
        .setTitle(paramsMap["title"])
        .setMessage(paramsMap["message"])
        .setNeutralButton(paramsMap["neutral_text"]) { dialog, _ ->
            neutralAction(dialog)
        }
        .setNegativeButton(paramsMap["negative_text"]) { dialog, _ ->
            negativeAction(dialog)
        }
        .setPositiveButton(paramsMap["positive_text"]) { dialog, _ ->
            positiveAction(dialog)
        }
        .show()
}