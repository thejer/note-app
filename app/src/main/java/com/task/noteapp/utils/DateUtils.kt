package com.task.noteapp.utils

import com.task.noteapp.utils.DateUtils.FormatPattern.DISPLAY_DATE_FORMAT
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    object FormatPattern {
        const val DISPLAY_DATE_FORMAT = "dd/mm/yyyy"
    }

    fun getCurrentDate(): String {
        val calendar = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat(
            DISPLAY_DATE_FORMAT,
            Locale.getDefault()
        )
        return simpleDateFormat.format(calendar.time)
    }
}
