package com.xingray.viewteam

import android.util.SparseArray
import android.util.SparseIntArray
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import java.util.*

class ViewTeam(container: ViewGroup, autoInTeam: Boolean = false) {

    companion object {
        val TEAM_ID_NONE: Int = -1
        val TEAM_ID_DEFAULT: Int = 0
    }

    private val mContainer: ViewGroup = container
    private val mTeams = SparseArray<MutableList<View>>()
    private var mCurrentId: Int = TEAM_ID_NONE

    private val mTeamChangedListeners: LinkedList<((Int, Int) -> Unit)> by lazy { LinkedList<((Int, Int) -> Unit)>() }
    private val mInitializerList: SparseArray<(List<View>) -> Unit> by lazy { SparseArray<(List<View>) -> Unit>() }
    private val mLazyInflateLayoutIdList: SparseIntArray by lazy { SparseIntArray() }
    private val mLazyMergeLayoutIdList: SparseIntArray by lazy { SparseIntArray() }

    constructor(container: ViewGroup) : this(container, false)

    init {
        if (autoInTeam) {
            autoInTeam()
        }
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

    fun inTeam(teamId: Int, views: Sequence<View>): ViewTeam {
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

    fun lazyInflate(teamId: Int, layoutId: Int): ViewTeam {
        mLazyInflateLayoutIdList.put(teamId, layoutId)
        return this
    }

    fun lazyMerge(teamId: Int, layoutId: Int): ViewTeam {
        mLazyMergeLayoutIdList.put(teamId, layoutId)
        return this
    }

    fun setTeam(teamId: Int): ViewTeam {
        if (mCurrentId == teamId) {
            return this
        }

        val oldTeam = mCurrentId
        mTeams.safetyGet(mCurrentId).updateVisibility(View.GONE)
        mCurrentId = teamId

        val mergeLayoutId = mLazyMergeLayoutIdList[mCurrentId]
        if (mergeLayoutId != 0) {
            merge(mCurrentId, mergeLayoutId)
            mLazyMergeLayoutIdList.removeByKey(mCurrentId)
        }

        val inflateLayoutId = mLazyInflateLayoutIdList[mCurrentId]
        if (inflateLayoutId != 0) {
            inflate(mCurrentId, inflateLayoutId)
            mLazyInflateLayoutIdList.removeByKey(mCurrentId)
        }

        val list = mTeams.safetyGet(mCurrentId)
        list.updateVisibility(View.VISIBLE)

        val initializer = mInitializerList[mCurrentId]
        if (initializer != null) {
            initializer.invoke(list)
            mInitializerList.remove(mCurrentId)
        }

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

    fun removeViewsOfTeam(teamId: Int): ViewTeam {
        val views = mTeams.get(teamId) ?: return this
        mContainer.removeViews(views)
        views.clear()
        mTeams.delete(teamId)
        if (mCurrentId == teamId) {
            setTeam(TEAM_ID_DEFAULT)
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

    fun addInitializer(teamId: Int, initializer: Initializer): ViewTeam {
        mInitializerList.put(teamId, initializer::init)
        return this
    }

    fun addInitializer(teamId: Int, initializer: (List<View>) -> Unit): ViewTeam {
        mInitializerList.put(teamId, initializer)
        return this
    }

    private fun notifyTeamChanged(oldTeam: Int, newTeam: Int) {
        mTeamChangedListeners.forEach {
            it.invoke(oldTeam, newTeam)
        }
    }

    private fun autoInTeam() {
        if (mContainer.childCount == 0) {
            return
        }
        inTeam(TEAM_ID_DEFAULT, mContainer.children)
        setTeam(TEAM_ID_DEFAULT)
    }

    private fun setVisibility(view: View, teamId: Int) {
        view.visibility = if (teamId == mCurrentId) View.VISIBLE else View.GONE
    }
}