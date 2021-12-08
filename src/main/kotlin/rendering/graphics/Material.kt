package rendering.graphics

import RESOURCE_FOLDER_PATH
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL13
import org.newdawn.slick.opengl.Texture
import org.newdawn.slick.opengl.TextureLoader
import java.io.File
import java.io.IOException
import kotlin.system.exitProcess

class Material(private val path: String) {
    private var fullPath = ""

    private var texture: Texture? = null
    private var width: Float = 0F
    private var height: Float = 0F
    private var textureID: Int = 0

    fun create() {
        fullPath = RESOURCE_FOLDER_PATH + path
        try {
            texture = TextureLoader.getTexture(
                path.split("[.]")[path.length - 1], File(fullPath).inputStream(), GL11.GL_LINEAR
            )
        } catch (e: IOException) {
            System.err.println("Error: Can not find File at $fullPath")
            e.printStackTrace()
        }

        if (texture == null) {
            System.err.println("Error: Textuer is null")
            exitProcess(1)
        }

        width = texture!!.width
        height = texture!!.height
        textureID = texture!!.textureID
    }

    fun destroy() {
        GL13.glDeleteTextures(textureID)
    }

    fun getTexture(): Texture {
        if (texture == null) {
            System.err.println("Error: Textuer is null")
            exitProcess(1)
        }
        return texture!!
    }

    fun getWidth(): Float {
        return width
    }

    fun getHeight(): Float {
        return height
    }

    fun getTextureID(): Int {
        return textureID
    }

    fun getFullPath(): String {
        return fullPath
    }
}