package rendering.maths

import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class Vector2f(var x: Float, var y: Float) {
    fun set(newX: Float, newY: Float) {
        x = newX
        y = newY
    }

    fun setAngle(distance: Float, angleZ: Float): Vector2f {

        val newAngleX = cos(toDegrees(angleZ)) * distance
        val newAngleY = sin(toDegrees(angleZ)) * distance

        x += newAngleX
        y += newAngleY

        return Vector2f(x, y)
    }

    private fun toDegrees(angle: Float): Float {
        return (((angle + 360F) % 360F) / 360F) * (PI.toFloat() * 2F)
    }
}