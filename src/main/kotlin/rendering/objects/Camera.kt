package rendering.objects

import rendering.maths.Vector3f

class Camera(var position: Vector3f, var rotation: Vector3f) {

    fun update() {
        rotation.z += 0.5F
    }
}