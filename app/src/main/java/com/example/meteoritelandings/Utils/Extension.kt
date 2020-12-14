package com.example.meteoritelandings.Utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Kush Singh Chibber on 14-12-2020.
 */
fun Double.format(digits: Int): String = java.lang.String.format("%.${digits}f", this)

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View =
        LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)

fun Date.toSimpleString(): String =
        SimpleDateFormat("dd.MM.yyy", Locale.ENGLISH).format(this)