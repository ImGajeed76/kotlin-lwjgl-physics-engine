package rendering

import ASPECT
import CAMERA
import FAR
import FOV
import FPS_THROTTLE
import FULLSCREEN
import NEAR
import SCREEN_HEIGHT
import SCREEN_WIDTH
import SHOW_FPS_IN_TITLE
import input.Input
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWWindowSizeCallback
import org.lwjgl.opengl.GL.createCapabilities
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL11.glClearColor
import rendering.maths.Matrix4f
import kotlin.math.cos
import kotlin.math.sin


class GameWindow(var width: Int = 100, var height: Int = 100, var name: String = "Game Window") {
    var window = glfwCreateWindow(width, height, name, 0, 0)
    var input = Input()

    var projectionMatrix: Matrix4f = Matrix4f()

    private var clearColor: Color = Color().black()
    private var windowOpen = true

    var FPS = 0F
    var frames = 0F
    var fpsTimer: Long = System.currentTimeMillis()

    private var sizeCallback = object : GLFWWindowSizeCallback() {
        override fun invoke(window: Long, w: Int, h: Int) {
            width = w
            height = h
            GL11.glViewport(0, 0, width, height)
        }
    }
    private var isFullscreen = false
    private var lastIsFullScreen = false

    private var lastPosX = IntArray(1)
    private var lastPosY = IntArray(1)
    private var lastWidth = 0
    private var lastHeight = 0

    fun init() {
        if (!glfwInit()) {
            println("Error: GLFW Failed to init window!")
        }

        window = glfwCreateWindow(width, height, name, 0, 0)
        glfwMakeContextCurrent(window)
        createCapabilities()
        GL11.glEnable(GL11.GL_DEPTH_TEST)

        createCallbacks()

        glfwShowWindow(window)
        if (FPS_THROTTLE) {
            glfwSwapInterval(1)
        } else {
            glfwSwapInterval(0)
        }

    }

    fun destroy() {
        windowOpen = false
    }

    fun setClearColor(color: Color) {
        clearColor = color
    }

    fun update() {
        ASPECT = width.toFloat() / height.toFloat()
        projectionMatrix = Matrix4f().projection(FOV, ASPECT, NEAR, FAR)

        updateFPS()
        checkExit()
        input.update()

        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT or GL11.GL_DEPTH_BUFFER_BIT)
        glClearColor(clearColor.r, clearColor.g, clearColor.b, clearColor.a)
        glfwPollEvents()

        changeFullscreen()
        showFullscreen()
    }

    fun updateCam() {
        if (input.isKeyDown(GLFW_KEY_W)) {
            //CAMERA.position.x -= CAMERA.cameraSpeed * sin(-CAMERA.rotation.y)
            //CAMERA.position.y -= CAMERA.cameraSpeed * sin(-CAMERA.rotation.x)
            //CAMERA.position.z -= CAMERA.cameraSpeed * -cos(-CAMERA.rotation.y)
            CAMERA.position.z -= CAMERA.cameraSpeed
        }

        if (input.isKeyDown(GLFW_KEY_S)) {
            //CAMERA.position.x += CAMERA.cameraSpeed * sin(-CAMERA.rotation.y)
            //CAMERA.position.y += CAMERA.cameraSpeed * sin(-CAMERA.rotation.x)
            //CAMERA.position.z += CAMERA.cameraSpeed * -cos(-CAMERA.rotation.y)
            CAMERA.position.z += CAMERA.cameraSpeed
        }

        if (input.isKeyDown(GLFW_KEY_A)) {
            //CAMERA.position.z -= CAMERA.cameraSpeed * sin(-CAMERA.rotation.y)
            //CAMERA.position.x -= CAMERA.cameraSpeed * -cos(-CAMERA.rotation.y)
            CAMERA.position.x -= CAMERA.cameraSpeed
        }

        if (input.isKeyDown(GLFW_KEY_D)) {
            //CAMERA.position.z += CAMERA.cameraSpeed * sin(-CAMERA.rotation.y)
            //CAMERA.position.x += CAMERA.cameraSpeed * -cos(-CAMERA.rotation.y)
            CAMERA.position.x += CAMERA.cameraSpeed
        }

        if (input.isKeyDown(GLFW_KEY_LEFT_SHIFT) || input.isKeyDown(GLFW_KEY_Q)) {
            CAMERA.position.y -= CAMERA.cameraSpeed
        }

        if (input.isKeyDown(GLFW_KEY_SPACE) || input.isKeyDown(GLFW_KEY_E)) {
            CAMERA.position.y += CAMERA.cameraSpeed
        }

        val mouseX = input.getMouseX() - width / 2
        val mouseY = input.getMouseY() - height / 2

        CAMERA.rotation.x += (mouseX * CAMERA.cameraSpeed).toFloat()
        CAMERA.rotation.y += (mouseY * CAMERA.cameraSpeed).toFloat()

        input.setCursorPos(window, (width / 2).toDouble(), (height / 2).toDouble())
    }

    fun swapBuffers() {
        glfwSwapBuffers(window)
    }

    fun isWindowOpen(): Boolean {
        return windowOpen && !glfwWindowShouldClose(window)
    }

    private fun createCallbacks() {
        glfwSetKeyCallback(window, input.getKeyboardCallback())
        glfwSetCursorPosCallback(window, input.getMouseMoveCallback())
        glfwSetMouseButtonCallback(window, input.getMouseButtonCallback())
        glfwSetWindowSizeCallback(window, sizeCallback)
    }

    private fun updateFPS() {
        frames++
        if (System.currentTimeMillis() > fpsTimer + 1000) {
            FPS = frames
            fpsTimer = System.currentTimeMillis()
            frames = 0F
        }

        if (SHOW_FPS_IN_TITLE) {
            glfwSetWindowTitle(window, "$name | FPS: $FPS")
        }
    }

    private fun checkExit() {
        if (input.isKeyPressed(GLFW_KEY_ESCAPE)) {
            destroy()
        }
    }

    fun setFullscreen(fullscreen: Boolean) {
        isFullscreen = fullscreen
    }

    private fun changeFullscreen() {
        if (input.isKeyPressed(GLFW_KEY_F11) || input.isKeyPressed(GLFW_KEY_F)) {
            FULLSCREEN = !FULLSCREEN
            setFullscreen(FULLSCREEN)
        }
    }

    private fun showFullscreen() {
        if (isFullscreen && lastIsFullScreen != isFullscreen) {
            lastWidth = width
            lastHeight = height
            glfwGetWindowPos(window, lastPosX, lastPosY)
            glfwSetWindowMonitor(window, glfwGetPrimaryMonitor(), 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, 0)
        } else if (lastIsFullScreen != isFullscreen) {
            glfwSetWindowMonitor(window, 0, lastPosX[0], lastPosY[0], lastWidth, lastHeight, 0)
        }

        lastIsFullScreen = isFullscreen
    }
}


