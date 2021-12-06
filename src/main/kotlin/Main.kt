import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL11.*
import rendering.Color
import rendering.GameWindow
import rendering.Mesh
import rendering.Shader
import java.util.*

const val SECS_PER_UPDATE = 1.0 / 30.0
val GAME = GameWindow(500, 500)
var MY_COLORS: Dictionary<String, Color> = Hashtable()

var vertices: ArrayList<Float> = arrayListOf()
var uvs: ArrayList<Float> = arrayListOf()

fun main() {
    vertices = arrayListOf(
        -0.5F, 0.5F, 0F, 0.5F, 0.5F, 0F, 0.5F, -0.5F, 0F,

        0.5F, -0.5F, 0F, -0.5F, -0.5F, 0F, -0.5F, 0.5F, 0F
    )

    uvs = arrayListOf(
        0F, 0F, 0F, 1F, 0F, 0F, 1F, 1F, 0F, 1F, 1F, 0F, 0F, 1F, 0F, 0F, 0F, 0F
    )

    GAME.init()
    val clear = Color().white()
    val mesh = Mesh(vertices, uvs)
    val shader = Shader("DefaultShader")

    while (!glfwWindowShouldClose(GAME.window)) {
        glfwPollEvents()

        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
        glClearColor(clear.r, clear.g, clear.b, clear.a)

        shader.bind()
        shader.setUniform("matColor", Color().grey())
        mesh.render()
        shader.unBind()

        glfwSwapBuffers(GAME.window)
    }

    mesh.cleanUp()
    glfwTerminate()
}