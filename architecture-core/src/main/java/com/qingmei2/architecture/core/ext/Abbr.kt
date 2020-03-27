package com.qingmei2.architecture.core.ext

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View

/**
 * Author: SmartYu
 * EMail : luyu1235211@163.com
 * Date : 2020/3/27
 * Desc :
 */

inline fun <reified T : Activity> Activity.gotoActivity() {
    val i = Intent(this, T::class.java)
    startActivity(i)
}

inline fun <reified T : Activity> Activity.gotoActivityForResult(requestcode: Int) {
    val i = Intent(this, T::class.java)
    startActivityForResult(i, requestcode)
}

inline fun <reified T : Activity> Activity.gotoActivity(key: String, bundle: Bundle) {
    val i = Intent(this, T::class.java)
    i.putExtra(key, bundle)
    startActivity(i)
}

inline fun <reified T : Activity> Activity.gotoActivityForResult(key: String, bundle: Bundle, requestcode: Int) {
    val i = Intent(this, T::class.java)
    i.putExtra(key, bundle)
    startActivityForResult(i, requestcode)
}

//filter frequent click event
fun View.clickWithLimit(block: ((v: View?) -> Unit)) {
    setOnClickListener(object : View.OnClickListener {
        var last = 0L
        override fun onClick(v: View?) {
            if (System.currentTimeMillis() - last > 500) {
                block(v)
                last = System.currentTimeMillis()
            }
        }
    })
}
fun View.clickWithLimit(intervalMill:Int, block: ((v: View?) -> Unit)) {
    setOnClickListener(object : View.OnClickListener {
        var last = 0L
        override fun onClick(v: View?) {
            if (System.currentTimeMillis() - last > intervalMill) {
                block(v)
                last = System.currentTimeMillis()
            }
        }
    })
}