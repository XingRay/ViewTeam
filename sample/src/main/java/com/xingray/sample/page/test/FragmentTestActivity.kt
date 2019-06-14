package com.xingray.sample.page.test

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.xingray.sample.R

class FragmentTestActivity : Activity() {

    companion object {
        fun start(activity: Activity) {
            val starter = Intent(activity.applicationContext, FragmentTestActivity::class.java)
            activity.startActivity(starter)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_test)
    }
}