package math.tween

import math.MathHelper.lerp

interface SpringHandler: TweenHandler {
    companion object {
        fun create(initialValue: Double) = object : TweenHandler {
            override var value: Double = initialValue

            var endValue: Double
                get() = tweenData?.end ?: value
                set(value) = if (tweenData != null)
                    tweenData!!.end = value
                    else Unit

            private var tweenData: TweenData? = null

            override fun update(currentTime: Long) {
                tweenData?.let { data ->
                    val elapsedTime = currentTime - data.startTime
                    val duration = data.info.duration.toDouble()

                    if (duration <= 0 || elapsedTime >= duration) {
                        value = data.end
                        tweenData = null
                        return
                    }

                    val iter = (elapsedTime / duration).toInt()
                    val timeNormal = (elapsedTime - iter * duration) / duration

                    if (data.info.repeats >= 0 && iter >= data.info.repeats) {
                        value = if (data.info.reverses && (data.info.repeats % 2 == 1))
                            data.start
                        else
                            data.end
                        tweenData = null
                        return
                    }

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

    private data class TweenData(
        val start: Double,
        var end: Double,
        val info: TweenInfo,
        val startTime: Long
    )
}