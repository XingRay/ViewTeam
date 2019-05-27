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
    private val mTeamChangedListeners: LinkedList<((Int, Int) -> Unit)> by lazy {
        LinkedList<((Int, Int) -> Unit)>()
    }

    init {
        autoRegister()
    }

    fun addView(teamId: Int, view: View): ViewTeam {
        mContainer.addView(view)
        mTeams.safetyGet(teamId).add(view)
        setVisibility(view, teamId)
        return this
    }

    fun inTeam(teamId: Int, vararg viewIds: Int): ViewTeam {
        return inTeam(teamId, viewIds.map {
            mContainer.findViewById<View>(it)
        })
    }

    fun inTeam(teamId: Int, vararg views: View): ViewTeam {
        inTeam(teamId, views.map { it })
        return this
    }

    fun inTeam(teamId: Int, views: Iterable<View>): ViewTeam {
        mTeams.safetyGet(teamId).addAll(views)
        views.forEach {
            setVisibility(it, teamId)
        }
        getCurrentViews()?.forEach { it.visibility = View.VISIBLE }
        return this
    }

    fun addAll(teamId: Int, views: Iterable<View>): ViewTeam {
        mContainer.addViews(views)
        mTeams.safetyGet(teamId).addAll(views)
        views.forEach {
            setVisibility(it, teamId)
        }
        return this
    }

    fun addAll(teamId: Int, views: Array<View>): ViewTeam {
        mContainer.addViews(views)
        mTeams.safetyGet(teamId).addAll(views)
        views.forEach {
            setVisibility(it, teamId)
        }
        return this
    }

    fun inflate(teamId: Int, layoutId: Int): ViewTeam {
        val views = mContainer.addLayout(layoutId)
        mTeams.safetyGet(teamId).addAll(views)
        views.forEach {
            setVisibility(it, teamId)
        }
        return this
    }

    fun merge(teamId: Int, layoutId: Int): ViewTeam {
        val views = mContainer.merge(layoutId)
        mTeams.safetyGet(teamId).addAll(views)
        views.forEach {
            setVisibility(it, teamId)
        }
        return this
    }

    fun setTeam(teamId: Int): ViewTeam {
        if (mCurrentId == teamId) {
            return this
        }

        val oldTeam = mCurrentId
        mTeams.safetyGet(mCurrentId).updateVisibility(View.GONE)
        mCurrentId = teamId
        mTeams.safetyGet(mCurrentId).updateVisibility(View.VISIBLE)
        notifyTeamChanged(oldTeam, teamId)

        return this
    }

    fun getTeamId(): Int {
        return mCurrentId
    }

    fun getViewsOfTeam(teamId: Int): List<View>? {
        return mTeams.get(teamId)
    }

    fun getCurrentViews(): List<View>? {
        return getViewsOfTeam(mCurrentId)
    }

    fun removeTeam(teamId: Int): ViewTeam {
        val views = mTeams.get(teamId) ?: return this
        mContainer.removeViews(views)
        views.clear()
        mTeams.delete(teamId)
        if (mCurrentId == teamId) {
            setTeam(0)
        }
        return this
    }

    fun addTeamChangedListener(listener: TeamChangeListener): ViewTeam {
        mTeamChangedListeners.add(listener::onTeamChanged)
        return this
    }

    fun addTeamChangedListener(listener: (Int, Int) -> Unit): ViewTeam {
        mTeamChangedListeners.add(listener)
        return this
    }

    private fun notifyTeamChanged(oldTeam: Int, newTeam: Int) {
        mTeamChangedListeners.forEach {
            it.invoke(oldTeam, newTeam)
        }
    }

    private fun autoRegister() {
        if (mContainer.childCount == 0) {
            return
        }
        mTeams.safetyGet(0).addAll(mContainer.children.toLinkedList())
    }

    private fun setVisibility(view: View, teamId: Int) {
        view.visibility = if (teamId == mCurrentId) View.VISIBLE else View.GONE
    }
}