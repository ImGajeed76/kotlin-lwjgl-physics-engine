package rendering.objects

import rendering.graphics.Mesh
import rendering.maths.Vector3f

class GameObject(var position: Vector3f, var rotation: Vector3f, var scale: Vector3f, var mesh: Mesh) {
    fun update() {
        position.add(0F, 0F, 0F)
        rotation.add(0F, 0.1F, 0F)
    }

    fun create() {
        mesh.create()
    }

    fun destroy() {
        mesh.destroy()
    }
}