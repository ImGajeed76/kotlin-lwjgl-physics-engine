package rendering

import org.lwjgl.opengl.GL20.*
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException
import kotlin.system.exitProcess

class Shader(fileName: String) {
    private var program: Int = 0
    private var vs: Int = 0
    private var fs: Int = 0

    init {
        program = glCreateProgram()

        vs = glCreateShader(GL_VERTEX_SHADER)
        glShaderSource(vs, createShader("$fileName.vs"))
        glCompileShader(vs)
        if (glGetShaderi(vs, GL_COMPILE_STATUS) != 1) {
            System.err.println(glGetShaderInfoLog(vs))
            exitProcess(1)
        }

        fs = glCreateShader(GL_FRAGMENT_SHADER)
        glShaderSource(fs, createShader("$fileName.fs"))
        glCompileShader(fs)
        if (glGetShaderi(fs, GL_COMPILE_STATUS) != 1) {
            System.err.println(glGetShaderInfoLog(fs))
            exitProcess(1)
        }

        glAttachShader(program, vs)
        glAttachShader(program, fs)

        glBindAttribLocation(program, 0, "vertices")
        glBindAttribLocation(program, 1, "uv")

        glLinkProgram(program)
        if (glGetProgrami(program, GL_LINK_STATUS) != 1) {
            System.err.println(glGetProgramInfoLog(program))
            exitProcess(1)
        }

        glValidateProgram(program)
        if (glGetProgrami(program, GL_VALIDATE_STATUS) != 1) {
            System.err.println(glGetProgramInfoLog(program))
            exitProcess(1)
        }
    }

    private fun createShader(fileName: String): String {
        val sb: StringBuilder = StringBuilder()

        try {
            val br = BufferedReader(FileReader(File("src/main/resources/Shaders/$fileName")))
            var line: String?
            while (true) {
                line = br.readLine()
                if (line == null) {
                    break
                }

                sb.append(line)
                sb.append("\n")
            }
            br.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return sb.toString()
    }

    fun setUniform(name: String, color: Color) {
        val location = glGetUniformLocation(program, name)
        if (location != 1) {
            glUniform4f(location, color.r, color.g, color.b, color.a)
        }
    }

    fun bind() {
        glUseProgram(program)
    }

    fun unBind() {
        glUseProgram(0)
    }
}