package com.xingray.viewteam

/**
 * listener for team id changed by invoke [ViewTeam.setTeam]
 */
interface TeamChangeListener {
    /**
     * called when team id changed by invoke [ViewTeam.setTeam]
     * [oldTeamId] last team id before change
     * [newTeamId] the team id will set to be current team id
     */
    fun onTeamChanged(oldTeamId: Int, newTeamId: Int)
}