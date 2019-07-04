package com.xingray.viewteam

import android.view.View

/**
 * init callback for view team
 *
 * @author : leixing
 * @date : 2019/7/4 16:11
 * @version : 1.0.0
 * mail : leixing1012@qq.com
 *
 */
interface Initializer {
    fun init(views: List<View>)
}