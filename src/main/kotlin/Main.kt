import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL11.*
import rendering.GameWindow

val SECS_PER_UPDATE = 1.0 / 30.0
val GAME = GameWindow(500, 500)

fun main() {
    GAME.init()

    while (!glfwWindowShouldClose(GAME.window)) {
        glfwPollEvents()

        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
        glClearColor(1F, 1F, 1F, 1F)

        glfwSwapBuffers(GAME.window)
    }

    glfwTerminate()
}