package rendering.graphics.utils

import org.lwjgl.opengl.GL20.*
import org.lwjgl.system.MemoryUtil
import rendering.maths.Matrix4f
import rendering.maths.Vector2f
import rendering.maths.Vector3f
import java.nio.FloatBuffer

class Shader(vertexPath: String, fragmentPath: String) {
    private val fileUtils = FileUtils()

    private var vertexFile = ""
    private var fragmentFile = ""

    private var vertexID = 0
    private var fragmentID = 0
    private var programID = 0

    init {
        vertexFile = fileUtils.loadAsString(vertexPath)
        fragmentFile = fileUtils.loadAsString(fragmentPath)
    }

    fun create() {
        programID = glCreateProgram()

        vertexID = glCreateShader(GL_VERTEX_SHADER)
        glShaderSource(vertexID, vertexFile)
        glCompileShader(vertexID)

        if (glGetShaderi(vertexID, GL_COMPILE_STATUS) == GL_FALSE) {
            System.err.println("Error: Vertex Shader | ${glGetShaderInfoLog(vertexID)}")
            return
        }

        fragmentID = glCreateShader(GL_FRAGMENT_SHADER)
        glShaderSource(fragmentID, fragmentFile)
        glCompileShader(fragmentID)

        if (glGetShaderi(fragmentID, GL_COMPILE_STATUS) == GL_FALSE) {
            System.err.println("Error: Fragment Shader | ${glGetShaderInfoLog(fragmentID)}")
            return
        }

        glAttachShader(programID, vertexID)
        glAttachShader(programID, fragmentID)

        glLinkProgram(programID)
        if (glGetProgrami(programID, GL_LINK_STATUS) == GL_FALSE){
            System.err.println("Error: Program Linking | ${glGetProgramInfoLog(programID)}")
            return
        }

        glValidateProgram(programID)
        if (glGetProgrami(programID, GL_VALIDATE_STATUS) == GL_FALSE){
            System.err.println("Error: Program Validation | ${glGetProgramInfoLog(programID)}")
            return
        }

        glDeleteShader(vertexID)
        glDeleteShader(fragmentID)
    }

    fun getUniformLocation(name: String): Int {
        return glGetUniformLocation(programID, name)
    }

    fun setUniform(name: String, value: Float) {
        glUniform1f(getUniformLocation(name), value)
    }

    fun setUniform(name: String, value: Int) {
        glUniform1i(getUniformLocation(name), value)
    }

    fun setUniform(name: String, value: Boolean) {
        glUniform1i(getUniformLocation(name), value.toInt())
    }

    fun setUniform(name: String, value: Vector2f) {
        glUniform2f(getUniformLocation(name), value.x, value.y)
    }

    fun setUniform(name: String, value: Vector3f) {
        glUniform3f(getUniformLocation(name), value.x, value.y, value.z)
    }

    fun setUniform(name: String, value: Matrix4f) {
        val matrix: FloatBuffer = MemoryUtil.memAllocFloat(Matrix4f().size * Matrix4f().size)
        matrix.put(value.getAll()).flip()
        glUniformMatrix4fv(getUniformLocation(name), true, matrix)

    }

    fun bind() {
        glUseProgram(programID)
    }

    fun unbind() {
        glUseProgram(0)
    }

    fun destroy() {
        glDetachShader(programID, vertexID)
        glDetachShader(programID, fragmentID)
        glDeleteProgram(vertexID)
        glDeleteProgram(fragmentID)
        glDeleteProgram(programID)
    }
}

private fun Boolean.toInt(): Int {
    return if (this) {
        1
    }
    else {
        0
    }
}
