package me.deska.deskalib.math

object MathHelper {
    fun lerp(start: Double, end: Double, t: Double): Double = start + (end - start) * t
}