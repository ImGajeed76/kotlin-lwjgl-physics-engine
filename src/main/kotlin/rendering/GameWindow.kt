package rendering

import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL.createCapabilities
import org.lwjgl.opengl.GL11.glClearColor


class GameWindow(var width: Int = 100, var height: Int = 100, var name: String = "Game Window") {
    var window = glfwCreateWindow(width, height, name, 0, 0)

    fun init() {
        if (!glfwInit()) {
            println("Error: GLFW Failed to init window!")
        }

        window = glfwCreateWindow(width, height, name, 0, 0)
        glfwShowWindow(window)
        glfwMakeContextCurrent(window)

        createCapabilities()
        glClearColor(0F, 0F, 0F, 1F)
    }
}