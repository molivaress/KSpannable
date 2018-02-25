package com.github.programmerr47.kspannablesample


import android.content.Context
import android.support.v4.content.ContextCompat
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.URLSpan
import android.widget.Toast
import android.widget.Toast.*
import com.github.programmerr47.kspannable.clickableSpan
import com.github.programmerr47.kspannable.getSpannable

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

    private fun Context.showToast(message: String) = Toast.makeText(this, message, LENGTH_SHORT)
}
