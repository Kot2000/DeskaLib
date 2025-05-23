package me.deska.deskalib.math.tween

import me.deska.deskalib.math.MathHelper.lerp
import kotlin.math.floor

interface TweenHandler {
    companion object {
        fun create(initialValue: Double) = object : TweenHandler {
            override var value: Double = initialValue

            private var tweenData: TweenData? = null

            override fun update(currentTime: Long) {
                tweenData?.let { data ->

                    val elapsedTime = currentTime - data.startTime
                    val duration = data.info.duration.toDouble()
                    val maxDuration = duration * (data.info.repeats + 1)

                    if (duration <= 0 || elapsedTime >= maxDuration) {
                        value = if (data.info.reverses && (data.info.repeats % 2 == 1)) {
                            data.start
                        } else {
                            data.end
                        }
                        tweenData = null
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

    var value: Double

    fun update(currentTime: Long)
    fun tween(value: Double, startTime: Long, info: TweenInfo)
    fun clear()

    private data class TweenData(
        val start: Double,
        val end: Double,
        val info: TweenInfo,
        val startTime: Long
    )
}