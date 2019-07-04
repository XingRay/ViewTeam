package com.xingray.viewteam

import android.view.View

/**
 * view team handler to handle view in team when it in or out of team
 *
 * @author : leixing
 * @date : 2019/7/4 20:59
 * @version : 1.0.0
 * mail : leixing1012@qq.com
 *
 */
interface ViewTeamHandler {
    fun handleViewTeam(view: View, teamId: Int, currentTeamId: Int)
}