package com.mohaberabi.crimeapp.core.presentation.fragments

import android.app.DatePickerDialog
import android.app.Dialog
import android.icu.util.Calendar
import android.icu.util.GregorianCalendar
import android.os.Bundle
import android.widget.DatePicker
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import java.util.Date

class DatePickerFragment(
    private val onDatePicked: (Date) -> Unit
) : DialogFragment() {
    companion object {
        const val TAG = "DatePickerFragment"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calender = Calendar.getInstance()
        val initialYear = calender.get(Calendar.YEAR)
        val initialMonth = calender.get(Calendar.MONTH)
        val initialDay = calender.get(Calendar.DAY_OF_MONTH)

        val listener =
            DatePickerDialog.OnDateSetListener { _: DatePicker, year: Int, month: Int, day: Int ->
                val result = GregorianCalendar(year, month, day)
                onDatePicked(result.time)
            }
        return DatePickerDialog(
            requireContext(),
            listener,
            initialYear,
            initialMonth,
            initialDay,
        )


    }
}