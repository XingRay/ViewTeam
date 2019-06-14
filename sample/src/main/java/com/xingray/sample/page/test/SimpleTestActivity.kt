package com.xingray.sample.page.test

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import com.xingray.sample.R
import com.xingray.sample.page.util.showToast
import com.xingray.viewteam.TeamChangeListener
import com.xingray.viewteam.ViewTeam

class SimpleTestActivity : Activity() {

    companion object {
        private val TAG = SimpleTestActivity::class.java.simpleName

        fun start(activity: Activity) {
            val starter = Intent(activity.applicationContext, SimpleTestActivity::class.java)
            activity.startActivity(starter)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_simple_test)

        val views = intArrayOf(R.id.bt_0, R.id.bt_1, R.id.bt_2, R.id.bt_3).map { findViewById<View>(it) }
        val viewTeam = ViewTeam.of(findViewById(R.id.cl_root))
            .inflate(1, R.layout.team_01).inTeam(1, views)
            .addView(2, Button(applicationContext).apply {
                text = "team02"
                val constraintLayoutParams = ConstraintLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                constraintLayoutParams.leftToLeft = R.id.cl_root
                constraintLayoutParams.rightToRight = R.id.cl_root
                constraintLayoutParams.topToTop = R.id.cl_root
                constraintLayoutParams.bottomToBottom = R.id.cl_root
                layoutParams = constraintLayoutParams
            }).inTeam(2, views)
            .merge(3, R.layout.team_03).inTeam(3, views)

        findViewById<View>(R.id.bt_0).setOnClickListener {
            viewTeam.setTeam(0)
        }

        findViewById<View>(R.id.bt_1).setOnClickListener {
            viewTeam.setTeam(1)
        }

        findViewById<View>(R.id.bt_2).setOnClickListener {
            viewTeam.setTeam(2)
        }

        findViewById<View>(R.id.bt_3).setOnClickListener {
            viewTeam.setTeam(3)
        }

        viewTeam.addTeamChangedListener { old, new ->
            showToast("$old->$new")
        }.addTeamChangedListener(object : TeamChangeListener {
            override fun onTeamChanged(oldTeamId: Int, newTeamId: Int) {
                Log.i(TAG, "$oldTeamId->$newTeamId")
            }
        })
    }
}