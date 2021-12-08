package rendering.maths

import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

class Vector3f(var x: Float, var y: Float, var z: Float) {
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

    private fun toDegrees(angle: Float): Float {
        return (((angle + 360F) % 360F) / 360F) * (PI.toFloat() * 2F)
    }
}