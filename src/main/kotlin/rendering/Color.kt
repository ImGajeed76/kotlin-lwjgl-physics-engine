package rendering

import MY_COLORS

class Color(var r: Float = 1F, var g: Float = 1F, var b: Float = 1F, var a: Float = 1F) {
    fun addColor(color: Color, name: String) {
        MY_COLORS.put(name, color)
    }

    override fun toString(): String {
        return "($r, $g, $b, $a)"
    }

    // default colors \/

    fun black(): Color {
        return Color(0F, 0F, 0F)
    }

    fun white(): Color {
        return Color(1F, 1F, 1F)
    }

    fun grey(): Color {
        return Color(0.5F, 0.5F, 0.5F)
    }

    fun red(): Color {
        return Color(1F, 0F, 0F)
    }

    fun green(): Color {
        return Color(0F, 1F, 0F)
    }

    fun blue(): Color {
        return Color(0F, 0F, 1F)
    }

    fun magenta(): Color {
        return Color(1F, 0F, 1F)
    }

    fun yellow(): Color {
        return Color(1F, 1F, 0F)
    }

    fun cyan(): Color {
        return Color(0F, 1F, 1F)
    }

    // default colors /\
}