varying float x;
varying float y;
uniform float width;
uniform float height;
uniform float A, B, C;
uniform sampler2D txtr;

void main() {
	float CX = (x-0.5);
	float CY = (y-0.5);
	float theta = 0.0;
	float dx = CX*cos(theta)-CY*sin(theta);
	float dy = CX*sin(theta)+CY*cos(theta);
	float r = sqrt(dx*dx+dy*dy);

	float corr = 1-A*r*r-B*r*r*r-C*r*r*r*r;
	float xu = (dx)*(corr)+0.5;
	float yu = (dy)*(corr)+0.5;

	vec4 color = texture(txtr,vec2(xu,yu),0.0);
	if ( length(color.xyz)<0.6 )
		gl_FragColor = vec4(0,0,0,1);
	else
		gl_FragColor = vec4(1,1,1,1);
}