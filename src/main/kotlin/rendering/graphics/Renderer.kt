package rendering.graphics

import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL30.*
import rendering.graphics.utils.Shader

class Renderer(private val shader: Shader) {
    fun renderMesh(mesh: Mesh) {
        glBindVertexArray(mesh.vao)

        glEnableVertexAttribArray(0)
        glEnableVertexAttribArray(1)
        glEnableVertexAttribArray(2)

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, mesh.ibo)

        glActiveTexture(GL_TEXTURE0)
        glBindTexture(GL_TEXTURE_2D, mesh.material.getTextureID())

        shader.bind()
        GL11.glDrawElements(GL_TRIANGLES, mesh.indices.size, GL_UNSIGNED_INT, 0)
        shader.unbind()

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0)

        glDisableVertexAttribArray(0)
        glDisableVertexAttribArray(1)
        glDisableVertexAttribArray(2)

        glBindVertexArray(0)
    }
}