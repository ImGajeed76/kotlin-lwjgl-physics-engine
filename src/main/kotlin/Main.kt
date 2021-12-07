import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFW.glfwTerminate
import rendering.Color
import rendering.GameWindow
import rendering.graphics.Mesh
import rendering.graphics.Renderer
import rendering.graphics.Vertex
import rendering.graphics.utils.Shader
import rendering.maths.Vector3f
import java.awt.Dimension
import java.awt.Toolkit
import java.util.*

//Window
val screenSize: Dimension = Toolkit.getDefaultToolkit().screenSize
val SCREEN_WIDTH = screenSize.width
val SCREEN_HEIGHT = screenSize.height
val GAME = GameWindow(500, 500)
var MY_COLORS: Dictionary<String, Color> = Hashtable()
var FULLSCREEN: Boolean = false

//Input
var mousePosTimer: Long = System.currentTimeMillis()

//FPS
const val FPS_THROTTLE = false
const val SHOW_FPS_IN_TITLE = true

//Graphics
var vertices: Array<Vertex> = arrayOf(
    Vertex(Vector3f(-0.5F, 0.5F, 0.0F), Color().magenta().toVector3f()),
    Vertex(Vector3f(0.5F, 0.5F, 0.0F), Color().blue().toVector3f()),
    Vertex(Vector3f(0.5F, -0.5F, 0.0F), Color().cyan().toVector3f()),
    Vertex(Vector3f(-0.5F, -0.5F, 0.0F), Color().green().toVector3f()),
)
var indices: IntArray = arrayOf(
    0, 1, 2,
    0, 3, 2,
).toIntArray()
var mesh = Mesh(vertices, indices)
var shader = Shader("src/main/resources/Shaders/DefaultVertex.glsl", "src/main/resources/Shaders/DefaultFragment.glsl")
val renderer = Renderer(shader)

fun main() {
    GAME.init()
    GAME.setClearColor(Color().red())
    GAME.setFullscreen(FULLSCREEN)

    mesh = Mesh(vertices, indices)
    mesh.create()
    shader.create()

    while (GAME.isWindowOpen()) {
        GAME.update()
        update()
    }

    close()
}

fun update() {
    renderer.renderMesh(mesh)
    GAME.swapBuffers()
}

fun printMousePos(delayMillis: Int) {
    if (System.currentTimeMillis() > mousePosTimer + delayMillis) {
        println("X = ${GAME.input.getMouseX()}, Y = ${GAME.input.getMouseY()}")
        mousePosTimer = System.currentTimeMillis()
    }
}

fun close() {
    mesh.destroy()
    shader.destroy()
    GAME.input.destroy()
    GLFW.glfwWindowShouldClose(GAME.window)
    GLFW.glfwDestroyWindow(GAME.window)
    glfwTerminate()
}