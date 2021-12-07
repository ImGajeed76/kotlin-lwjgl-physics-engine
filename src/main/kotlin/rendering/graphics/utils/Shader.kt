package rendering.graphics.utils

import org.lwjgl.opengl.GL20.*

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

    fun bind() {
        glUseProgram(programID)
    }

    fun unbind() {
        glUseProgram(0)
    }

    fun destroy() {
        glDeleteProgram(programID)
    }
}