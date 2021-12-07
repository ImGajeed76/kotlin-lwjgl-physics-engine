package rendering.graphics

import rendering.maths.Vector3f

class Vertex(private var pos: Vector3f, private var color: Vector3f) {
    fun getPosition(): Vector3f {
        return pos
    }

    fun getColor(): Vector3f {
        return color
    }
}