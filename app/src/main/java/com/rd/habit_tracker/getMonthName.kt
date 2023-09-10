package com.rd.habit_tracker

fun getMonthName(monthNumber: Int): String {
    val monthNames = arrayOf(
        "January", "February", "March", "April",
        "May", "June", "July", "August",
        "September", "October", "November", "December"
    )

    return if (monthNumber in 1..12) {
        monthNames[monthNumber - 1]
    } else {
        "Invalid month number"
    }
}
