package com.github.programmerr47.kspannablesample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import android.text.Spanned.*
import android.text.method.LinkMovementMethod
import android.text.method.MovementMethod
import android.widget.Toast
import android.widget.Toast.*
import com.github.programmerr47.kspannable.*
import com.github.programmerr47.kspannablesample.Sample.construct
import com.github.programmerr47.kspannablesample.Sample.constructNew
import kotlinx.android.synthetic.main.activity_sample.*

class SampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)
        tv_text.text = constructNew(this)
        tv_text.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun makeEveryWordIsClickable(text: CharSequence) {
        val clickableText = SpannableString(text)
        text.forEachWord { word, start, end ->
            clickableText.setSpan(clickableSpan {
                Toast.makeText(this, "Clicked word: $word", LENGTH_SHORT)
            }, start, end, SPAN_INCLUSIVE_EXCLUSIVE)
        }
    }

    private fun CharSequence.forEachWord(action: (CharSequence, Int, Int) -> Unit) {
        var index = 0
        forEachIndexed { i, char ->
            if (char.isWhitespace()) {
                if (i != index) {
                    action(subSequence(index, i), index, i)
                }
                index = i + 1
            }
        }

        if (index < length) {
            action(subSequence(index, length), index, length)
        }
    }
}
