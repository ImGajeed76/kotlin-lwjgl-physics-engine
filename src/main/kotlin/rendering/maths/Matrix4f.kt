package rendering.maths

class Matrix4f {
    var size: Int = 4
    var elements: FloatArray = floatArrayOf()

    fun get(x: Int, y: Int): Float {
        return elements[y * size + x]
    }

    fun get(x: Int, y: Int, value: Float) {
        elements[y * size + x] = value
    }

    fun getAll(): FloatArray {
        return elements
    }
}