package math.tween

@FunctionalInterface
interface TweenType<T> {
    fun tween(start: T, end: T, info: TweenInfo): T
}
