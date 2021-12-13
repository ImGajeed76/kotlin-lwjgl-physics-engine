import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFW.glfwTerminate
import rendering.Color
import rendering.GameWindow
import rendering.graphics.Material
import rendering.graphics.Mesh
import rendering.graphics.Renderer
import rendering.graphics.Vertex
import rendering.graphics.Shader
import rendering.maths.Vector2f
import rendering.maths.Vector3f
import rendering.objects.Camera
import rendering.objects.GameObject
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

// Cam
var ASPECT: Float = 0F
const val FOV: Float = 70F
const val NEAR: Float = 0.1F
const val FAR: Float = 1000F
val CAMERA = Camera(Vector3f(0F, 0F, 1F), Vector3f(0F, 0F, 0F))

//Input
var mousePosTimer: Long = System.currentTimeMillis()

//FPS
const val FPS_THROTTLE = true
const val SHOW_FPS_IN_TITLE = true

//Graphics
var vertices: Array<Vertex> = arrayOf(
    Vertex(Vector3f(-0.5F, 0.5F, 0F), Color().red().toVector3f(), Vector2f(0F, 0F)),
    Vertex(Vector3f(-0.5F, -0.5F,0F), Color().yellow().toVector3f(), Vector2f(0F, 1F)),
    Vertex(Vector3f(0.5F, -0.5F, 0F), Color().green().toVector3f(), Vector2f(1F, 1F)),
    Vertex(Vector3f(0.5F, 0.5F,  0F), Color().cyan().toVector3f(), Vector2f(1F, 0F)),

    Vertex(Vector3f(-0.5F, -0.5F, 0F), Color().red().toVector3f(), Vector2f(0F, 0F)),
    Vertex(Vector3f(-0.5F, -0.5F, -1F), Color().yellow().toVector3f(), Vector2f(0F, 1F)),
    Vertex(Vector3f(-0.5F, 0.5F, -1F), Color().green().toVector3f(), Vector2f(1F, 1F)),
    Vertex(Vector3f(-0.5F, 0.5F, 0F), Color().cyan().toVector3f(), Vector2f(1F, 0F)),
)
var indices: IntArray = arrayOf(
    0, 1, 2,
    0, 3, 2,

    4, 5, 6,
    4, 7, 6,
).toIntArray()

var material = Material("Textures/cool.png")

var mesh = Mesh(vertices, indices, material)
var shader = Shader("Shaders/DefaultVertex.glsl", "Shaders/DefaultFragment.glsl")
var gameObject = GameObject(Vector3f(0F, 0F, 0F), Vector3f(0F, 0F, 0F), Vector3f(1F, 1F, 1F), mesh)
var renderer = Renderer(shader)

fun main() {
    GAME.init()
    GAME.setClearColor(Color().white())
    GAME.setFullscreen(FULLSCREEN)

    gameObject.mesh = Mesh(vertices, indices, material)
    gameObject.create()
    shader.create()

    while (GAME.isWindowOpen()) {
        GAME.update()
        update()
    }

    close()
}

fun update() {
    gameObject.update()
    CAMERA.update()
    renderer.renderGameObject(gameObject)
    GAME.swapBuffers()
}

fun printMousePos(delayMillis: Int) {
    if (System.currentTimeMillis() > mousePosTimer + delayMillis) {
        println("X = ${GAME.input.getMouseX()}, Y = ${GAME.input.getMouseY()}")
        mousePosTimer = System.currentTimeMillis()
    }
}

fun close() {
    gameObject.destroy()
    shader.destroy()
    GAME.input.destroy()
    GLFW.glfwWindowShouldClose(GAME.window)
    GLFW.glfwDestroyWindow(GAME.window)
    glfwTerminate()
}