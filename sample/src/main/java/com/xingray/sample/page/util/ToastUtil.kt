package com.xingray.sample.page.util;

import android.app.Activity
import android.content.Context
import android.widget.Toast;

fun Activity.showToast(context: Context, s: CharSequence) {
    Toast.makeText(context, s, Toast.LENGTH_SHORT).show()
}
