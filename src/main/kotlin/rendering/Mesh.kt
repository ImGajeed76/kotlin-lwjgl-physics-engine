package rendering

import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL15
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL30.*
import java.nio.FloatBuffer

class Mesh(var vertices: ArrayList<Float>, var uvs: ArrayList<Float>) {
    private var vId: Int = 0
    private var uId: Int = 0
    private var vao: Int = 0

    init {
        vao = glGenVertexArrays()
        glBindVertexArray(vao)

        vId = glGenBuffers()
        glBindBuffer(GL_ARRAY_BUFFER, vId)
        glBufferData(GL_ARRAY_BUFFER, CreateBuffer(vertices), GL_STATIC_DRAW)
        glVertexAttribPointer(0, vertices.size / 3, GL_FLOAT, false, 0, 0)
        glBindBuffer(GL_ARRAY_BUFFER, 0)

        uId = glGenBuffers()
        glBindBuffer(GL_ARRAY_BUFFER, uId)
        glBufferData(GL_ARRAY_BUFFER, CreateBuffer(uvs), GL_STATIC_DRAW)

        glBindBuffer(GL_ARRAY_BUFFER, 0)
    }

    fun Render() {
        glBindVertexArray(vao)
        glEnableVertexAttribArray(0)
        glEnableVertexAttribArray(1)
        glEnableVertexAttribArray(2)

        glBindBuffer(GL_ARRAY_BUFFER, vId)
        glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0)

        glBindBuffer(GL_ARRAY_BUFFER, uId)
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0)

        glDrawArrays(GL_TRIANGLES, 0, 6)
        glBindBuffer(GL_ARRAY_BUFFER, 0)

        glDisableVertexAttribArray(0)
        glDisableVertexAttribArray(1)
        glDisableVertexAttribArray(2)
        glBindVertexArray(0)
    }

    fun CreateBuffer(data: ArrayList<Float>): FloatBuffer {
        var buffer: FloatBuffer = BufferUtils.createFloatBuffer(data.size)
        buffer.put(data.toFloatArray())
        buffer.flip()
        return buffer
    }

    fun CleanUp() {
        glDeleteVertexArrays(vao)
        GL15.glDeleteBuffers(vId)
    }
}
