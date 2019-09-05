package com.xingray.sample.page.main

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xingray.recycleradapter.LayoutId
import com.xingray.recycleradapter.RecyclerAdapter
import com.xingray.recycleradapter.ViewHolder
import com.xingray.sample.R
import com.xingray.sample.page.test.JavaTestActivity
import com.xingray.sample.page.test.RecyclerViewTestActivity
import com.xingray.sample.page.test.SimpleTestActivity

class MainActivity : AppCompatActivity() {

    var rvList: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvList = findViewById(R.id.rv_list)
        rvList?.layoutManager = LinearLayoutManager(applicationContext)
        val adapter = RecyclerAdapter(applicationContext)
            .addType(TestViewHolder::class.java) { _, _, t ->
                t.task.invoke()
            }
        rvList?.adapter = adapter
        rvList?.addItemDecoration(
            DividerItemDecoration(
                applicationContext,
                DividerItemDecoration.VERTICAL
            )
        )

        adapter.update(listOf(
            Test("simple test") {
                SimpleTestActivity.start(this)
            },
            Test("java test") {
                JavaTestActivity.start(this)
            },
            Test("RecyclerView test") {
                RecyclerViewTestActivity.start(this)
            }
        ))
    }

    data class Test(
        val name: String,
        val task: () -> Unit
    )

    @LayoutId(R.layout.item_main_list)
    class TestViewHolder(itemView: View) : ViewHolder<Test>(itemView) {
        private val tvText: TextView? = itemView.findViewById(R.id.tv_text)

        override fun onBindItemView(t: Test, position: Int) {
            tvText?.text = t.name
        }
    }
}
