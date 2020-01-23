package br.com.lucascordeiro.core.ui.util

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorCompat
import androidx.core.view.ViewPropertyAnimatorListener
import br.com.lucascordeiro.core.R
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson

fun TextView.toBold() {
    this.text = "<b>${this.text}</b>".toHtml()
}

fun Any.toJson() : String{
    return Gson().toJson(this)
}

fun String.toHtml() : Spanned{
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
       Html.fromHtml(this, Html.FROM_HTML_MODE_COMPACT)
    } else {
      Html.fromHtml(this)
    }
}

fun String.showSnack(v: View, duration: Int = 1, backgroundColor: Int ) {
    val snack = Snackbar.make(v, this,if(duration == 1)  Snackbar.LENGTH_LONG else  Snackbar.LENGTH_SHORT)
    val view = snack.view
    view.setBackgroundColor(ContextCompat.getColor(v.context, backgroundColor))
    val tv = view.findViewById(R.id.snackbar_text) as TextView
    tv.setTextColor(Color.WHITE)
    snack.show()
}

fun View.hideScale(){
    ViewCompat.animate(this)
        .scaleY(0f).scaleX(0f)
        .setDuration(500)
        .setListener(object : ViewPropertyAnimatorListener {
            override fun onAnimationEnd(view: View?) {
                this@hideScale.visibility = View.GONE
            }

            override fun onAnimationCancel(view: View?) {
            }

            override fun onAnimationStart(view: View?) {
            }

        })
        .setInterpolator(DecelerateInterpolator()).start()
}

fun View.showScale(){
    visibility = View.VISIBLE

    ViewCompat.animate(this)
        .scaleY(1f).scaleX(1f)
        .setDuration(500)
        .setInterpolator(DecelerateInterpolator()).start()
}

fun View.hideAlpha(delay: Long = 1000L, startDelay: Long? = null, translate: Boolean = true){
    if(this.alpha != 0f) {
        val animation = ViewCompat.animate(this)
            .alpha(0f)
            .setDuration(delay)
            .setListener(object : ViewPropertyAnimatorListener {
                override fun onAnimationEnd(view: View?) {
                    visibility = View.GONE
                }

                override fun onAnimationCancel(view: View?) {
                }

                override fun onAnimationStart(view: View?) {
                }

            })
            .setInterpolator(DecelerateInterpolator())

        if (startDelay != null)
            animation.startDelay = startDelay

        if(translate)
            animation.translationY(-50f)

        animation.start()
    }
}

fun View.showAlpha(delay: Long = 1000L, startDelay: Long? = null, whioutMove: Boolean? = null){
    if(this.alpha != 1f) {
        val animation = ViewCompat.animate(this)
            .alpha(1f)
            .setDuration(delay)
            .setListener(object : ViewPropertyAnimatorListener {
                override fun onAnimationEnd(view: View?) {
                }

                override fun onAnimationCancel(view: View?) {
                }

                override fun onAnimationStart(view: View?) {
                    visibility = View.VISIBLE
                }

            })
            .setInterpolator(DecelerateInterpolator())
        if (startDelay != null)
            animation.startDelay = startDelay

        if(whioutMove==null){
            animation.translationY(50f)
        }else{
            if(!whioutMove)
                animation.translationY(50f)
        }
        animation.start()
    }
}

fun ViewGroup.showItemsWithMove(delay: Long = 300L){
    for (i in 0 until this.childCount) {

        val v = this.getChildAt(i)

        if (v !is ImageView && v !is LinearLayout) {
            val viewAnimator: ViewPropertyAnimatorCompat

            if (v !is Button) {
                viewAnimator = ViewCompat.animate(v)
                    .translationY(50f).alpha(1f)
                    .setStartDelay(delay * i)
                    .setDuration(1000)
            } else {
                viewAnimator = ViewCompat.animate(v)
                    .scaleY(1f).scaleX(1f)
                    .setStartDelay(delay * i)
                    .setDuration(500)
            }

            viewAnimator.setInterpolator(DecelerateInterpolator()).start()
        }
    }
}

fun ViewGroup.showItems(delay: Long = 300L){
    for (i in 0 until this.childCount) {

        val v = this.getChildAt(i)

        if (v !is ImageView) {
            val viewAnimator: ViewPropertyAnimatorCompat

            if (v !is Button) {
                viewAnimator = ViewCompat.animate(v)
                    .translationY(50f).alpha(1f)
                    .setStartDelay(delay * i + 500)
                    .setDuration(1000)
            } else {
                viewAnimator = ViewCompat.animate(v)
                    .scaleY(1f).scaleX(1f)
                    .setStartDelay(delay * i + 500)
                    .setDuration(500)
            }

            viewAnimator.setInterpolator(DecelerateInterpolator()).start()
        }
    }
}

fun ViewGroup.hideItems(delay: Long = 300L){
    for (i in 0 until this.childCount) {

        val v = this.getChildAt(i)

        if (v !is ImageView) {
            val viewAnimator: ViewPropertyAnimatorCompat

            if (v !is Button) {
                viewAnimator = ViewCompat.animate(v)
                    .translationY(-50f).alpha(0f)
                    .setStartDelay(delay * i + 500)
                    .setDuration(1000)
            } else {
                viewAnimator = ViewCompat.animate(v)
                    .scaleY(0f).scaleX(0f)
                    .setStartDelay(delay * i + 500)
                    .setDuration(500)
            }

            viewAnimator.setInterpolator(DecelerateInterpolator()).start()
        }
    }
}

fun View.animateHeight(height: Int, duration: Long = 300L){
    val anim = ValueAnimator.ofInt(this.measuredHeight, height)
    anim.addUpdateListener { valueAnimator ->
        val `val` = valueAnimator.animatedValue as Int
        val layoutParams = this.layoutParams
        layoutParams.height = `val`
        this.layoutParams = layoutParams
    }
    anim.duration = duration
    anim.start()
}

fun View.animateScale(height: Int, width: Int, duration: Long = 300L){
    val animH = ValueAnimator.ofInt(this.measuredHeight, height)
    animH.addUpdateListener { valueAnimator ->
        val `val` = valueAnimator.animatedValue as Int
        val layoutParams = this.layoutParams
        layoutParams.height = `val`
        this.layoutParams = layoutParams
    }
    animH.duration = duration
    animH.start()

    val animW = ValueAnimator.ofInt(this.measuredWidth, width)
    animW.addUpdateListener { valueAnimator ->
        val `val` = valueAnimator.animatedValue as Int
        val layoutParams = this.layoutParams
        layoutParams.width = `val`
        this.layoutParams = layoutParams
    }
    animW.duration = duration
    animW.start()
}

fun String.unMaskOnlyNumbers() : String {
    return this.replace("[^0-9]".toRegex(), "")
}

fun String.toBold() : Spanned {
    return "<b>${this}</b>".toHtml()
}

fun String.toHtmlColor(color: String) : Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml("<font color=$color>${this}</font>", Html.FROM_HTML_MODE_COMPACT)
    } else {
        Html.fromHtml(this)
    }
}