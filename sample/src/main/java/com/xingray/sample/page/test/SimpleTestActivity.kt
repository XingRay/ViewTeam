package com.xingray.sample.page.test

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import com.xingray.sample.R
import com.xingray.viewteam.ViewTeam

class SimpleTestActivity : Activity() {

    var viewTeam: ViewTeam? = null

    companion object {
        fun start(context: Context) {
            val intent = Intent()
            intent.setClass(context, SimpleTestActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_simple_test)

        val llRoot: LinearLayout? = findViewById(R.id.ll_container)
        if (llRoot != null) {
            viewTeam = ViewTeam.of(llRoot)
                .inflate(1, R.layout.team_01)
                .add(2, Button(applicationContext).apply {
                    text = "team02"
                }).merge(3, R.layout.team_linearlayout)
        }

        val bt: Button? = findViewById(R.id.bt_test)
        val etInput: EditText? = findViewById(R.id.et_input)

        bt?.setOnClickListener {
            viewTeam?.setTeam(etInput?.text.toString().toInt())
        }
    }
}