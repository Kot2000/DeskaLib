package me.deska.deskalib.math.tween

import me.deska.deskalib.math.MathHelper.lerp
import kotlin.math.floor

interface SpringHandler : TweenHandler {
    companion object {
        fun create(initialValue: Double) = object : SpringHandler {
            override var value: Double = initialValue

            private var tweenData: TweenData? = null

            override var endValue: Double
                get() = tweenData?.end ?: value
                set(newEnd) {
                    tweenData?.end = newEnd
                }

            override fun update(currentTime: Long) {
                tweenData?.let { data ->
                    val elapsedTime = currentTime - data.startTime
                    val duration = data.info.duration.toDouble()

                    if (duration <= 0.0) {
                        value = data.end
                        return
                    }

                    val iter = floor(elapsedTime / duration).toInt()
                    val timeNormal = (elapsedTime - iter * duration) / duration

                    val reversed = data.info.reverses && (iter % 2 == 1)
                    val t = if (reversed) 1.0 - timeNormal else timeNormal

                    val progress = data.info.tweenType.bezier.getY(t)
                    value = lerp(data.start, data.end, progress)
                }
            }

            override fun tween(value: Double, startTime: Long, info: TweenInfo) {
                tweenData = TweenData(this.value, value, info, startTime)
            }

            override fun clear() {
                tweenData = null
            }
        }
    }

    var endValue: Double

    private data class TweenData(
        var start: Double,
        var end: Double,
        val info: TweenInfo,
        val startTime: Long
    )
}