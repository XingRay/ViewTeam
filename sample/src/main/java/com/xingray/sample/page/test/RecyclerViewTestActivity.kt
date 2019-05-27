package com.xingray.sample.page.test

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.leixing.recycleradapter.BaseViewHolder
import com.leixing.recycleradapter.RecyclerAdapter
import com.xingray.sample.R
import com.xingray.viewteam.ViewTeam

class RecyclerViewTestActivity : Activity() {
    companion object {
        fun start(context: Context) {
            val intent = Intent()
            intent.setClass(context, RecyclerViewTestActivity::class.java)
            context.startActivity(intent)
        }
    }

    var rvList: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recyclerview_test)

        val list = findViewById<RecyclerView>(R.id.rv_list)

        rvList = list
        if (list != null) {
            list.layoutManager = LinearLayoutManager(applicationContext)
            val adapter = RecyclerAdapter<Data, DataViewHolder>(applicationContext)
                .itemLayoutId(R.layout.item_recyclerview_test_item)
                .viewHolderFactory { itemView, _ -> DataViewHolder(itemView) }
                .itemClickListener { _, position, t -> showToast("$position clicked: ${t?.text}") }
            list.adapter = adapter
            list.addItemDecoration(DividerItemDecoration(applicationContext, DividerItemDecoration.VERTICAL))

            val dataList = mutableListOf<Data>()
            for (i in 0 until 100) {
                dataList.add(Data(String.format("%03d", i)))
            }
            adapter.update(dataList)
        }


        val button = Button(applicationContext)
        button.text = "error"
        val constraintLayoutParams = ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        constraintLayoutParams.leftToLeft = R.id.cl_root
        constraintLayoutParams.rightToRight = R.id.cl_root
        constraintLayoutParams.topToTop = R.id.cl_root
        constraintLayoutParams.bottomToBottom = R.id.cl_root
        button.layoutParams = constraintLayoutParams

        val views = intArrayOf(R.id.bt_0, R.id.bt_1, R.id.bt_2, R.id.bt_3)
        val viewTeam = ViewTeam.of(findViewById(R.id.cl_root))
            .merge(1, R.layout.view_team_empty).inTeam(1, *views)
            .inflate(2, R.layout.view_team_loading).inTeam(2, *views)
            .addView(3, button).inTeam(3, *views)

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
    }

    private fun showToast(s: String) {
        Toast.makeText(applicationContext, s, Toast.LENGTH_SHORT).show()
    }

    data class Data(val text: String)

    class DataViewHolder(itemView: View) : BaseViewHolder<Data>(itemView) {
        private val tvText: TextView? = itemView.findViewById(R.id.tv_text)
        override fun onBindItemView(t: Data?, position: Int) {
            tvText?.text = t?.text
        }
    }
}