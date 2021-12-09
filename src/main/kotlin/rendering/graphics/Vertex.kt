package rendering.graphics

import rendering.maths.Vector2f
import rendering.maths.Vector3f

class Vertex(private var pos: Vector3f, private var color: Vector3f, private var textureCoord: Vector2f) {
    fun getPosition(): Vector3f {
        return pos
    }

    fun setPosition(vector3f: Vector3f) {
        pos = vector3f
    }

    fun getColor(): Vector3f {
        return color
    }

    fun getTextureCoord(): Vector2f {
        return textureCoord
    }
}