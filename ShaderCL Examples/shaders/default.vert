//
// http://www.khronos.org/opengles/sdk/docs/reference_cards/OpenGL-ES-2_0-Reference-card.pdf
//

attribute float px;
attribute float py;
varying float x;
varying float y;

void main(){
	gl_Position = gl_ModelViewProjectionMatrix*vec4(px,py,1,1);
    x = px;
    y = py;
}