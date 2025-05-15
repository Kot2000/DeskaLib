package math.tween

import math.CubicBezier

enum class TweenTypes(val bezier: CubicBezier) {
    LINEAR(CubicBezier(0.0, 0.0, 1.0, 1.0)),
    EASE(CubicBezier(0.25, 0.1, 0.25, 1.0)),
    EASE_IN(CubicBezier(0.42, 0.0, 1.0, 1.0)),
    EASE_OUT(CubicBezier(0.0, 0.0, 0.58, 1.0)),
    EASE_IN_OUT(CubicBezier(0.42, 0.0, 0.58, 1.0))
}