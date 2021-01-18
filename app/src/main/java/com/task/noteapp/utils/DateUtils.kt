package com.task.noteapp.utils

import com.task.noteapp.utils.DateUtils.FormatPattern.DISPLAY_DATE_FORMAT
import com.task.noteapp.utils.DateUtils.FormatPattern.DISPLAY_DATE_TIME_FORMAT
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    object FormatPattern {
        const val DISPLAY_DATE_FORMAT = "dd/mm/yyyy"
        const val DISPLAY_DATE_TIME_FORMAT = "dd/mm/yyyy hh:mm a"
    }

    fun getCurrentDate(isDateOnly: Boolean): String {
        val calendar = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat(
            if (isDateOnly)DISPLAY_DATE_FORMAT else DISPLAY_DATE_TIME_FORMAT,
            Locale.getDefault()
        )
        return simpleDateFormat.format(calendar.time)
    }
}
