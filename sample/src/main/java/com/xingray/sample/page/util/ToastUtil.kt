@file:JvmName("ToastUtil")
package com.xingray.sample.page.util;

import android.app.Activity
import android.widget.Toast

fun Activity.showToast(s: CharSequence) {
    Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
}
