import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFW.glfwTerminate
import rendering.Color
import rendering.GameWindow
import rendering.graphics.Material
import rendering.graphics.Mesh
import rendering.graphics.Renderer
import rendering.graphics.Vertex
import rendering.graphics.utils.Shader
import rendering.maths.Vector2f
import rendering.maths.Vector3f
import java.awt.Dimension
import java.awt.Toolkit
import java.util.*

//Variables
const val RESOURCE_FOLDER_PATH = "src/main/resources/"

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
    Vertex(Vector3f(0F, 0F, 0F).setAngle(distanceZ = 250F, angleX = 0F, angleY = 0F, angleZ = 60F * -0, distanceX = 0F, distanceY = 0F).toWindowPoint(), Color().red().toVector3f(), Vector2f(0F, 0F)),
    Vertex(Vector3f(0F, 0F, 0F).setAngle(distanceZ = 250F, angleX = 0F, angleY = 0F, angleZ = 60F * -1, distanceX = 0F, distanceY = 0F).toWindowPoint(), Color().yellow().toVector3f(), Vector2f(0F, 0F)),
    Vertex(Vector3f(0F, 0F, 0F).setAngle(distanceZ = 250F, angleX = 0F, angleY = 0F, angleZ = 60F * -2, distanceX = 0F, distanceY = 0F).toWindowPoint(), Color().green().toVector3f(), Vector2f(0F, 0F)),
    Vertex(Vector3f(0F, 0F, 0F).setAngle(distanceZ = 250F, angleX = 0F, angleY = 0F, angleZ = 60F * -3, distanceX = 0F, distanceY = 0F).toWindowPoint(), Color().cyan().toVector3f(), Vector2f(0F, 0F)),
    Vertex(Vector3f(0F, 0F, 0F).setAngle(distanceZ = 250F, angleX = 0F, angleY = 0F, angleZ = 60F * -4, distanceX = 0F, distanceY = 0F).toWindowPoint(), Color().blue().toVector3f(), Vector2f(0F, 0F)),
    Vertex(Vector3f(0F, 0F, 0F).setAngle(distanceZ = 250F, angleX = 0F, angleY = 0F, angleZ = 60F * -5, distanceX = 0F, distanceY = 0F).toWindowPoint(), Color().magenta().toVector3f(), Vector2f(0F, 0F)),
    Vertex(Vector3f(0F, 0F, 0F).toWindowPoint(), Color().white().toVector3f(), Vector2f(0F, 0F)),
)
var indices: IntArray = arrayOf(
    0, 1, 6,
    1, 2, 6,
    2, 3, 6,
    3, 4, 6,
    4, 5, 6,
    5, 0, 6,
).toIntArray()
var material = Material("Textures/cool.png")

var mesh = Mesh(vertices, indices, material)
var shader = Shader("Shaders/DefaultVertex.glsl", "Shaders/DefaultFragment.glsl")
val renderer = Renderer(shader)

fun main() {
    GAME.init()
    GAME.setClearColor(Color().white())
    GAME.setFullscreen(FULLSCREEN)

    mesh = Mesh(vertices, indices, material)
    mesh.create()
    shader.create()

    while (GAME.isWindowOpen()) {
        GAME.update()
        update()
    }

    close()
}

fun update() {
    mesh.destroy()
    mesh = Mesh(vertices, indices, material)
    mesh.create()
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