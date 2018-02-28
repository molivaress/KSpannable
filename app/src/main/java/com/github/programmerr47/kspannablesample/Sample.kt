package com.github.programmerr47.kspannablesample


import android.content.Context
import android.support.annotation.ColorRes
import android.support.annotation.DimenRes
import android.support.v4.content.ContextCompat
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.URLSpan
import android.widget.Toast
import android.widget.Toast.*
import com.github.programmerr47.kspannable.SpannableStringCreator
import com.github.programmerr47.kspannable.clickableSpan
import com.github.programmerr47.kspannable.getSpannable
import com.github.programmerr47.kspannable.resSpans
import com.github.programmerr47.kspannablesample.Sample.showToast

object Sample {

    fun construct(context: Context): CharSequence {
        val termsOfService = context.getString(R.string.terms_of_service)
        val privacyPolicy = context.getString(R.string.privacy_policy)

        val phrase = context.getString(R.string.terms, termsOfService, privacyPolicy)
        val termsOfServicesStart = phrase.indexOf(termsOfService)
        val privacyPolicyStart = phrase.indexOf(privacyPolicy)
        val spannable = SpannableStringBuilder(phrase)
        spannable.setSpan(URLSpan("terms_of_service_url"), termsOfServicesStart, termsOfServicesStart + termsOfService.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        spannable.setSpan(URLSpan("privacy_policy_url"), privacyPolicyStart, privacyPolicyStart + privacyPolicy.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        return spannable
    }

    fun constructNew(context: Context): CharSequence {
        return context.resources.getSpannable(R.string.terms,
                context.getString(R.string.terms_of_service) to listOf(URLSpan("terms_of_service_url")),
                context.getString(R.string.privacy_policy) to listOf(URLSpan("privacy_policy_url")))
    }

    fun construct2(context: Context): CharSequence {
        val firstSent = "This is the first sentence."
        val secondSent = "This is the second sentence."
        val thirdSent = "This is the third sentence."

        var index = 0
        val spannable = SpannableStringBuilder()
        spannable.append(firstSent)
        spannable.setSpan(clickableSpan { context.showToast("clicked on first") }, index, index + firstSent.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        spannable.setSpan(ForegroundColorSpan(ContextCompat.getColor(context, R.color.colorPrimary)), index, index + firstSent.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        spannable.setSpan(AbsoluteSizeSpan(context.resources.getDimensionPixelSize(R.dimen.first_size)), index, index + firstSent.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        spannable.append("\n")

        index = spannable.length
        spannable.append(secondSent)
        spannable.setSpan(clickableSpan { context.showToast("clicked on second") }, index, index + secondSent.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        spannable.setSpan(ForegroundColorSpan(ContextCompat.getColor(context, R.color.colorPrimaryDark)), index, index + secondSent.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        spannable.setSpan(AbsoluteSizeSpan(context.resources.getDimensionPixelSize(R.dimen.second_size)), index, index + secondSent.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        spannable.append("\n")

        index = spannable.length
        spannable.append(thirdSent)
        spannable.setSpan(clickableSpan { context.showToast("clicked on third") }, index, index + thirdSent.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        spannable.setSpan(ForegroundColorSpan(ContextCompat.getColor(context, R.color.colorAccent)), index, index + thirdSent.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        spannable.setSpan(AbsoluteSizeSpan(context.resources.getDimensionPixelSize(R.dimen.third_size)), index, index + thirdSent.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)

        return spannable
    }

    fun construct2Refactored(context: Context): CharSequence {
        val firstSent = "This is the first sentence."
        val secondSent = "This is the second sentence."
        val thirdSent = "This is the third sentence."

        val spannable = SpannableStringBuilder()
        spannable.append(context, firstSent, R.color.colorPrimary, R.dimen.first_size) { context.showToast("clicked on first") }
        spannable.append("\n")
        spannable.append(context, secondSent, R.color.colorPrimaryDark, R.dimen.second_size) { context.showToast("clicked on second") }
        spannable.append("\n")
        spannable.append(context, thirdSent, R.color.colorAccent, R.dimen.third_size) { context.showToast("clicked on third") }
        return spannable
    }

    fun SpannableStringBuilder.append(
            context: Context,
            text: CharSequence,
            @ColorRes textColorRes: Int,
            @DimenRes textSizeRes: Int,
            clickAction: () -> Unit) {
        val index = length
        append(text)
        setSpan(clickableSpan(clickAction), index, index + text.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        setSpan(ForegroundColorSpan(ContextCompat.getColor(context, textColorRes)), index, index + text.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        setSpan(AbsoluteSizeSpan(context.resources.getDimensionPixelSize(textSizeRes)), index, index + text.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
    }

    fun constructFinal(context: Context): CharSequence {
        return SpannableStringCreator()
                .append("This is the first sentence.", context.resSpans {
                    color(R.color.colorPrimary)
                    size(R.dimen.first_size)
                    click { context.showToast("clicked on first") }
                })
                .appendLn("", context.resSpans {
                    color(R.color.colorPrimaryDark)
                    size(R.dimen.second_size)
                    click { context.showToast("clicked on second") }
                })
                .appendLn("", context.resSpans {
                    color(R.color.colorAccent)
                    size(R.dimen.third_size)
                    click { context.showToast("clicked on third") }
                })
                .toSpannableString()
    }

    private fun Context.showToast(message: String) = Toast.makeText(this, message, LENGTH_SHORT).show()
}
