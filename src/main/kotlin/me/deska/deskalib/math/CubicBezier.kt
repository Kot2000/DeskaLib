package me.deska.deskalib.math

import kotlin.math.abs

data class CubicBezier(
    val x1: Double, val y1: Double,
    val x2: Double, val y2: Double
) {
    fun getY(tNorm: Double): Double {
        val t = solveForT(tNorm)
        return bezier(t, 0.0, y1, y2, 1.0)
    }

    private fun bezier(t: Double, a: Double, b: Double, c: Double, d: Double): Double {
        val mt = 1 - t
        return mt * mt * mt * a +
                3 * mt * mt * t * b +
                3 * mt * t * t * c +
                t * t * t * d
    }

    private fun bezierDeriv(t: Double, a: Double, b: Double, c: Double, d: Double): Double {
        val mt = 1 - t
        return 3 * mt * mt * (b - a) +
                6 * mt * t * (c - b) +
                3 * t * t * (d - c)
    }

    private fun solveForT(x: Double): Double {
        var t = x
        repeat(10) {
            val xEst = bezier(t, 0.0, x1, x2, 1.0)
            val dx = bezierDeriv(t, 0.0, x1, x2, 1.0)
            if (dx == 0.0) return t
            val tNext = t - (xEst - x) / dx
            if (abs(tNext - t) < 1e-6) return tNext
            t = tNext
        }
        return t
    }
}
