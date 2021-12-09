package rendering.maths

import GAME
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

class Vector3f(var x: Float, var y: Float, var z: Float, var toGWPoint: Boolean = false) {
    var toGameWindowPoint = toGWPoint

    fun set(newX: Float, newY: Float, newZ: Float) {
        x = newX
        y = newY
        z = newZ
    }

    fun setAngle(distance: Float, angleX: Float, angleY: Float, angleZ: Float): Vector3f {

        var newAngleX = cos(toDegrees(angleZ)) * distance
        var newAngleY = sin(toDegrees(angleZ)) * distance

        newAngleY += sin(toDegrees(angleX)) * distance
        var newAngleZ = cos(toDegrees(angleX)) * distance

        newAngleZ += cos(toDegrees(angleY)) * distance
        newAngleX += sin(toDegrees(angleY)) * distance

        x += newAngleX
        y += newAngleY
        z += newAngleZ

        return Vector3f(x, y, z)
    }

    fun toWindowPoint(): Vector3f {
        return Vector3f(x, y, z, true)
    }

    fun toGameWindowPoint(): Vector3f {
        val newX = (x / GAME.width) * 1
        val newY = (y / GAME.height) * 1

        return Vector3f(newX, newY, z)
    }

    private fun toDegrees(angle: Float): Float {
        return (((angle + 360F) % 360F) / 360F) * (PI.toFloat() * 2F)
    }

    override fun toString(): String {
        return "$x, $y, $z"
    }
}