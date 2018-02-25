package com.github.programmerr47.kspannable

import android.content.Context
import android.content.res.Resources
import android.support.annotation.StringRes
import java.lang.IndexOutOfBoundsException
import java.util.*

class SpannableAppendable(
        val creator: SpannableStringCreator,
        vararg spanParts: Pair<Any, Iterable<Any>>) : Appendable {

    private val spansMap = spanParts.toMap().mapKeys { it.key.let { it as? CharSequence ?: it.toString() } }

    override fun append(csq: CharSequence?) = apply { creator.appendSmart(csq, spansMap) }

    override fun append(csq: CharSequence?, start: Int, end: Int) = apply {
        if (csq != null) {
            if (start in 0..(end - 1) && end <= csq.length) {
                append(csq.subSequence(start, end))
            } else {
                throw IndexOutOfBoundsException("start " + start + ", end " + end + ", s.length() " + csq.length)
            }
        }
    }

    override fun append(c: Char) = apply { creator.append(c.toString()) }

    private fun SpannableStringCreator.appendSmart(csq: CharSequence?, spanDict: Map<CharSequence, Iterable<Any>>) {
        if (csq != null) {
            if (csq in spanDict) {
                append(csq, spanDict[csq]!!)
            } else {
                val possibleMatchDict = spanDict.filter { it.key.toString() == csq }
                if (possibleMatchDict.isNotEmpty()) {
                    val spanDictEntry = possibleMatchDict.entries.toList()[0]
                    append(spanDictEntry.key, spanDictEntry.value)
                } else {
                    append(csq)
                }
            }
        }
    }
}

fun Resources.getText(@StringRes id: Int, vararg formatArgs: Any?) =
        getSpannable(id, *formatArgs.filterNotNull().map { it to emptyList<Any>() }.toTypedArray())

fun Resources.getSpannable(@StringRes id: Int, vararg spanParts: Pair<Any, Iterable<Any>>): CharSequence {
    val resultCreator = SpannableStringCreator()
    Formatter(
            SpannableAppendable(resultCreator, *spanParts),
            configuration.locale)
            .format(getString(id), *spanParts.map { it.first }.toTypedArray())
    return resultCreator.toSpannableString()
}