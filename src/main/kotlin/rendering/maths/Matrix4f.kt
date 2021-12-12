package rendering.maths

import kotlin.math.cos
import kotlin.math.sin

class Matrix4f {
    var size: Int = 4
    var elements: FloatArray = FloatArray(size * size)

    fun identity(): Matrix4f {
        // Creates a Matrix like this:
        // 1000
        // 0100
        // 0010
        // 0001

        val result = Matrix4f()
        for (i in 0 until result.size) {
            for (j in 0 until result.size) {
                result.set(i, j, 0F)
            }
        }

        for (i in 0 until result.size) {
            result.set(i, i, 1F)
        }

        return result
    }

    fun translate(translate: Vector3f): Matrix4f {
        // Creates a Matrix like this:
        // 100x
        // 010y
        // 001z
        // 0001

        val result = Matrix4f().identity()

        result.set(3, 0, translate.x)
        result.set(3, 1, translate.y)
        result.set(3, 2, translate.z)

        return result
    }

    fun rotate(angle: Float, axis: Vector3f): Matrix4f {
        val result = Matrix4f().identity()

        val sin = sin(Math.toRadians(angle.toDouble())).toFloat()
        val cos = cos(Math.toRadians(angle.toDouble())).toFloat()
        val invCos = cos * -1

        result.set(0, 0, cos + axis.x * axis.x * invCos)
        result.set(0, 1, axis.x * axis.y * invCos - axis.z * sin)
        result.set(0, 2, axis.x * axis.z * invCos + axis.y * sin)
        result.set(1, 0, axis.y * axis.x * invCos + axis.z * sin)
        result.set(1, 1, cos + axis.y * axis.y * invCos)
        result.set(1, 2, axis.y * axis.z * invCos - axis.x * sin)
        result.set(2, 0, axis.z * axis.x * invCos - axis.y * sin)
        result.set(2, 1, axis.z * axis.y * invCos + axis.x * sin)
        result.set(2, 2, cos + axis.z * axis.z * invCos)

        return result
    }

    fun scale(scalar: Vector3f): Matrix4f {
        // Creates a Matrix like this:
        // x000
        // 0y00
        // 00z0
        // 0001

        val result = Matrix4f().identity()

        result.set(0, 0, scalar.x)
        result.set(1, 1, scalar.y)
        result.set(2, 2, scalar.z)

        return result
    }

    fun transform(position: Vector3f, rotation: Vector3f, scale: Vector3f): Matrix4f {
        val translationMatrix = Matrix4f().translate(position)
        val rotXMatrix = Matrix4f().rotate(rotation.x, Vector3f(1F, 0F, 0F))
        val rotYMatrix = Matrix4f().rotate(rotation.y, Vector3f(0F, 1F, 0F))
        val rotZMatrix = Matrix4f().rotate(rotation.z, Vector3f(0F, 0F, 1F))
        val scaleMatrix = Matrix4f().scale(scale)

        val rotationMatrix = Matrix4f().multiply(rotXMatrix, Matrix4f().multiply(rotYMatrix, rotZMatrix))

        return Matrix4f().multiply(translationMatrix, Matrix4f().multiply(rotationMatrix, scaleMatrix))
    }

    fun multiply(matrix: Matrix4f, other: Matrix4f): Matrix4f {
        val result = Matrix4f().identity()

        for (i in 0 until result.size) {
            for (j in 0 until result.size) {
                result.set(
                    i, j, matrix.get(i, 0) * other.get(0, j) +
                            matrix.get(i, 1) * other.get(1, j) +
                            matrix.get(i, 2) * other.get(2, j) +
                            matrix.get(i, 3) * other.get(3, j)
                )
            }
        }

        return result
    }

    fun get(x: Int, y: Int): Float {
        return elements[y * size + x]
    }

    fun set(x: Int, y: Int, value: Float) {
        elements[y * size + x] = value
    }

    fun getAll(): FloatArray {
        return elements
    }
}