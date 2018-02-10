package com.github.programmerr47.kspannable

import android.content.Context
import android.support.annotation.ColorRes
import android.support.annotation.DimenRes
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.support.v7.content.res.AppCompatResources
import android.text.TextPaint
import android.text.style.*
import android.view.View
import java.util.*

class ResSpans(val context: Context) : Iterable<Any> {
    val spans = ArrayList<Any>()

    override fun iterator() = spans.iterator()

    fun size(@DimenRes id: Int) =
            spans.add(AbsoluteSizeSpan(context.resources.getDimension(id).toInt()))

    fun color(@ColorRes id: Int) =
            spans.add(ForegroundColorSpan(ContextCompat.getColor(context, id)))

    fun icon(@DrawableRes id: Int, size: Int) =
            spans.add(ImageSpan(AppCompatResources.getDrawable(context, id)!!.apply {
                setBounds(0, 0, size, size)
            }))

    fun sansSerifMedium() = typeface("sans-serif-medium")
    fun sansSerifRegular() = typeface("sans-serif-regular")

    fun typeface(family: String) = spans.add(TypefaceSpan(family))
    fun typeface(style: Int) = spans.add(StyleSpan(style))

    fun click(action: () -> Unit) = spans.add(object : ClickableSpan() {
        override fun onClick(view: View) = action()

        override fun updateDrawState(ds: TextPaint?) {
            super.updateDrawState(ds)
            ds?.isUnderlineText = false
        }
    })

    fun custom(span: Any) = spans.add(span)
}

inline fun Context.resSpans(options: ResSpans.() -> Unit) =
        ResSpans(this).apply(options)
