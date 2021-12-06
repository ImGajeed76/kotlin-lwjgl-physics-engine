#version 140

in vec3 position;
in vec3 textureCoords;

uniform vec4 matColor;
out vec4 color;

void main()
{
    gl_Position = vec4(position, 1);
    color = matColor;
}