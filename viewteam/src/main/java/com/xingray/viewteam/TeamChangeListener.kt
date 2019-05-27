package com.xingray.viewteam

interface TeamChangeListener {
    fun onTeamChanged(oldTeamId: Int, newTeamId: Int)
}