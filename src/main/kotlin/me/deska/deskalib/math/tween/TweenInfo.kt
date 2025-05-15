package me.deska.deskalib.math.tween

interface TweenInfo {
    val duration: Long
    val tweenType: TweenTypes
    val repeats: Int
    val reverses: Boolean
}