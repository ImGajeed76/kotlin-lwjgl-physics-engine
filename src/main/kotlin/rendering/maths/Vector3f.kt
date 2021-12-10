package rendering.maths

import GAME
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class Vector3f(var x: Float, var y: Float, var z: Float) {
    fun set(newX: Float, newY: Float, newZ: Float) {
        x = newX
        y = newY
        z = newZ
    }

    fun setAngle(distanceX: Float, distanceY: Float, distanceZ: Float, angleX: Float, angleY: Float, angleZ: Float): Vector3f {

        var newAngleX = cos(toDegrees(angleZ)) * distanceZ
        var newAngleY = sin(toDegrees(angleZ)) * distanceZ

        newAngleY += sin(toDegrees(angleX)) * distanceX
        var newAngleZ = cos(toDegrees(angleX)) * distanceX

        newAngleZ += cos(toDegrees(angleY)) * distanceY
        newAngleX += sin(toDegrees(angleY)) * distanceY

        x += newAngleX
        y += newAngleY
        z += newAngleZ

        return Vector3f(x, y, z)
    }

    private fun toDegrees(angle: Float): Float {
        return (((angle + 360F) % 360F) / 360F) * (PI.toFloat() * 2F)
    }

    override fun toString(): String {
        return "$x, $y, $z"
    }
}