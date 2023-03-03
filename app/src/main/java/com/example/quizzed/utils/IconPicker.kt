package com.example.quizzed.utils

import com.example.quizzed.R


object IconPicker {
    val icons = arrayOf(
        R.drawable.ic_icon_6,
        R.drawable.ic_icon_3,
        R.drawable.ic_icon_4,
        R.drawable.ic_icon_5,
        R.drawable.ic_icon_8,
        R.drawable.ic_icon_9,
        R.drawable.blackedcap,
        R.drawable.book_icon


    )
    var currentIcon = 0

    fun getIcon(): Int {
        currentIcon = (currentIcon + 1) % icons.size
        return icons[currentIcon]
    }
}