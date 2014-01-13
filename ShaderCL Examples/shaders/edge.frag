varying float x;
varying float y;
uniform float width;
uniform float height;
uniform sampler2D txtr;

void main() {
	float dx = 1.0/width;
	float dy = 1.0/height;
	
	vec4 self = texture(txtr,vec2(x,y),0.0);
	
	vec4 n0 = texture(txtr,vec2(x-dx,y-dy),0.0);
	vec4 n1 = texture(txtr,vec2(x,y-dy),0.0);
	vec4 n2 = texture(txtr,vec2(x+dx,y-dy),0.0);
	
	vec4 n3 = texture(txtr,vec2(x-dx,y),0.0);
	vec4 n4 = texture(txtr,vec2(x+dx,y),0.0);
	
	vec4 n5 = texture(txtr,vec2(x-dx,y+dy),0.0);
	vec4 n6 = texture(txtr,vec2(x,y+dy),0.0);
	vec4 n7 = texture(txtr,vec2(x+dx,y+dy),0.0);
	
	vec4 GX = -1*n0+1*n2 -2*n3+2*n4 -1*n5+1*n7;
	vec4 GY = -1*n0+1*n5 -2*n1+2*n6 -1*n2+1*n7;
	
	float lx = length(GX.xyz);
	float ly = length(GY.xyz);
	float d = lx*lx+ly*ly;
	
	if ( d > 0.1 )
		gl_FragColor = vec4(d,d,d,1);
}