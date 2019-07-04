package com.xingray.viewteam

import android.util.SparseArray
import android.util.SparseIntArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import java.util.*

fun <T> Sequence<T>.toLinkedList(): LinkedList<T> {
    val list = LinkedList<T>()
    for (t in this) {
        list.add(t)
    }
    return list
}

fun ViewGroup.addLayout(layoutId: Int): MutableList<View> {
    val before = children.toList()
    LayoutInflater.from(context).inflate(layoutId, this, true)
    val after = children.toMutableList()
    after.removeAll(before)
    return after
}

fun ViewGroup.merge(layoutId: Int): MutableList<View> {
    val rootView = LayoutInflater.from(context).inflate(layoutId, null, false)
            as? ViewGroup ?: throw IllegalArgumentException("must merge viewGroup")

    if (!rootView.javaClass.isAssignableFrom(javaClass)) {
        throw IllegalArgumentException(
            "can not merge from ${rootView.javaClass.canonicalName} to ${javaClass.canonicalName}"
        )
    }

    val children = rootView.getChildren()
    rootView.removeAllViews()
    addViews(children)
    return children
}

fun ViewGroup.getChildren(): MutableList<View> {
    val views = mutableListOf<View>()
    for (view in children) {
        views.add(view)
    }
    return views
}

fun ViewGroup.addViews(views: Iterable<View>) {
    for (view in views) {
        addView(view)
    }
}

fun ViewGroup.addViews(views: Array<View>) {
    for (view in views) {
        addView(view)
    }
}

fun Iterable<View>.updateVisibility(visibility: Int) {
    forEach {
        it.visibility = visibility
    }
}

fun ViewGroup.removeViews(views: Iterable<View>) {
    views.forEach {
        removeView(it)
    }
}

fun <E> SparseArray<MutableList<E>>.safetyGet(key: Int): MutableList<E> {
    var list = get(key)
    if (list == null) {
        list = LinkedList()
        put(key, list)
    }
    return list
}

fun SparseIntArray.removeByKey(key: Int) {
    val index = indexOfKey(key)
    if (index < 0) {
        return
    }
    removeAt(index)
}
