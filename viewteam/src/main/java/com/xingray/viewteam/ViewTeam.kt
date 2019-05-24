package com.xingray.viewteam

import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import java.util.*

class ViewTeam private constructor(container: ViewGroup) {
    companion object {
        fun of(container: ViewGroup): ViewTeam {
            return ViewTeam(container)
        }
    }

    private val mContainer: ViewGroup = container
    private val mTeams = SparseArray<MutableList<View>>()
    private var mCurrentId: Int = 0

    init {
        autoRegister()
    }

    fun add(teamId: Int, view: View): ViewTeam {
        mContainer.addView(view)
        mTeams.safetyGet(teamId).add(view)
        view.visibility = if (teamId == mCurrentId) View.VISIBLE else View.GONE
        return this
    }

    fun addAll(teamId: Int, views: Iterable<View>): ViewTeam {
        mContainer.addViews(views)
        mTeams.safetyGet(teamId).addAll(views)
        views.forEach {
            it.visibility = if (teamId == mCurrentId) View.VISIBLE else View.GONE
        }
        return this
    }

    fun addAll(teamId: Int, views: Array<View>): ViewTeam {
        mContainer.addViews(views)
        mTeams.safetyGet(teamId).addAll(views)
        views.forEach {
            it.visibility = if (teamId == mCurrentId) View.VISIBLE else View.GONE
        }
        return this
    }

    fun inflate(teamId: Int, layoutId: Int): ViewTeam {
        val views = mContainer.addLayout(layoutId)
        mTeams.safetyGet(teamId).addAll(views)
        views.forEach {
            it.visibility = if (teamId == mCurrentId) View.VISIBLE else View.GONE
        }
        return this
    }

    fun merge(teamId: Int, layoutId: Int): ViewTeam {
        val views = mContainer.merge(layoutId)
        mTeams.safetyGet(teamId).addAll(views)
        views.forEach {
            it.visibility = if (teamId == mCurrentId) View.VISIBLE else View.GONE
        }
        return this
    }

    fun setTeam(teamId: Int): ViewTeam {
        if (mCurrentId == teamId) {
            return this
        }

        mTeams.get(mCurrentId).updateVisibility(View.GONE)
        mCurrentId = teamId
        mTeams.get(mCurrentId).updateVisibility(View.VISIBLE)

        return this
    }

    fun removeTeam(teamId: Int): ViewTeam {
        val views = mTeams.get(teamId)
        mContainer.removeViews(views)
        views.clear()
        mTeams.delete(teamId)
        if (mCurrentId == teamId) {
            setTeam(0)
        }
        return this
    }

    private fun autoRegister() {
        if (mContainer.childCount == 0) {
            return
        }
        var list = mTeams.get(0)
        if (list == null) {
            list = LinkedList()
            mTeams.put(0, list)
        }
        list.addAll(mContainer.children.toLinkedList())
    }
}