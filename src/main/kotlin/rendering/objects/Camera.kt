package rendering.objects

import GAME
import rendering.maths.Vector3f

class Camera(var position: Vector3f, var rotation: Vector3f, var cameraSpeed: Float = 0.02F) {

    fun update() {
        GAME.updateCam()
    }
}