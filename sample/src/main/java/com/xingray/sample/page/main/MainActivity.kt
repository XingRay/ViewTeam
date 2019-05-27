package com.xingray.sample.page.main

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.leixing.recycleradapter.BaseViewHolder
import com.leixing.recycleradapter.RecyclerAdapter
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
        val adapter = RecyclerAdapter<Test, TestViewHolder>(applicationContext)
            .itemLayoutId(R.layout.item_main_list)
            .viewHolderFactory { itemView, _ ->
                TestViewHolder(itemView)
            }.itemClickListener { _, _, t ->
                t?.task?.invoke()
            }
        rvList?.adapter = adapter
        rvList?.addItemDecoration(DividerItemDecoration(applicationContext, DividerItemDecoration.VERTICAL))

        adapter.update(listOf(
            Test("simple test") {
                SimpleTestActivity.start(applicationContext)
            },
            Test("java test") {
                JavaTestActivity.start(applicationContext)
            },
            Test("recyclerview test") {
                RecyclerViewTestActivity.start(applicationContext)
            }
//            Test("fragment test") {
//                FragmentTestActivity.start(applicationContext)
//            }
        ))
    }

    data class Test(
        val name: String,
        val task: () -> Unit
    )

    class TestViewHolder(itemView: View) : BaseViewHolder<Test>(itemView) {
        private val tvText: TextView? = itemView.findViewById(R.id.tv_text)

        override fun onBindItemView(t: Test?, position: Int) {
            tvText?.text = t?.name
        }
    }
}
