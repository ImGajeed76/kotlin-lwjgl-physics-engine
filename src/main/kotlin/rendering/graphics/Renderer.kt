package rendering.graphics

import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL30.*
import rendering.graphics.Shader
import rendering.maths.Matrix4f
import rendering.objects.GameObject

class Renderer(private val shader: Shader) {
    fun renderGameObject(gameObject: GameObject) {
        glBindVertexArray(gameObject.mesh.vao)

        glEnableVertexAttribArray(0)
        glEnableVertexAttribArray(1)
        glEnableVertexAttribArray(2)

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, gameObject.mesh.ibo)

        glActiveTexture(GL_TEXTURE0)
        glBindTexture(GL_TEXTURE_2D, gameObject.mesh.material.getTextureID())

        shader.bind()
        shader.setUniform("transfer", Matrix4f().transform(gameObject.position, gameObject.rotation, gameObject.scale))
        GL11.glDrawElements(GL_TRIANGLES, gameObject.mesh.indices.size, GL_UNSIGNED_INT, 0)
        shader.unbind()

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0)

        glDisableVertexAttribArray(0)
        glDisableVertexAttribArray(1)
        glDisableVertexAttribArray(2)

        glBindVertexArray(0)
    }
}