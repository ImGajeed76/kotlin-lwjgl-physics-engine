package rendering.objects

import rendering.graphics.Mesh
import rendering.maths.Vector3f
import kotlin.math.sin

class GameObject(var position: Vector3f, var rotation: Vector3f, var scale: Vector3f, var mesh: Mesh) {

    fun update() {
    }

    fun create() {
        mesh.create()
    }

    fun destroy() {
        mesh.destroy()
    }
}