package com.xingray.sample.page.test

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.xingray.sample.R

class FragmentTestActivity : Activity() {

    companion object {
        fun start(context: Context) {
            val intent = Intent()
            intent.setClass(context, FragmentTestActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_test)
    }
}