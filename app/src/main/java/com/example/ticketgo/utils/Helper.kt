package com.example.ticketgo.utils

import android.content.res.Resources
import android.view.View
import android.widget.HorizontalScrollView
import com.example.ticketgo.ui.events.Seat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun <T> Gson.convertToJsonString(t: T): String {
    return toJson(t).toString()
}

fun <T> Gson.convertToModel(jsonString: String, cls: Class<T>): T? {
    return try {
        fromJson(jsonString, cls)
    } catch (e: Exception) {
        null
    }
}

inline fun <reified T> Gson.fromJson(json: String) = this.fromJson<T>(json, object: TypeToken<T>() {}.type)

inline fun <reified T : View> HorizontalScrollView.scrollToPosition(
    tag: String?,
) {
    val view = findViewWithTag<T>(tag) ?: return
    val leftEdgePx = view.left
    val screenCenterPx = Resources.getSystem().displayMetrics.widthPixels / 2
    val scrollPx = if (leftEdgePx < screenCenterPx) 0
    else leftEdgePx - screenCenterPx + view.width / 2
    this.post {
        this.smoothScrollTo(scrollPx, view.top)
    }
}