varying float x;
varying float y;
uniform float width;
uniform float height;
uniform float pts[6];
uniform sampler2D txtr;

void main() {
	float dx = 1.0/width;
	float dy = 1.0/height;
	
	vec3 A = vec3(pts[0]*dx,pts[1]*dy,0);
	vec3 B = vec3(pts[2]*dx,pts[3]*dy,0);
	vec3 C = vec3(pts[4]*dx,pts[5]*dy,0);
	vec3 P = vec3(x,y,0);
	
	float norm = length(A-B);
	
	if ( sign(cross(A-B,A-P).z)==sign(cross(B-C,B-P).z) &&
	 	 sign(cross(B-C,B-P).z)==sign(cross(C-A,C-P).z) ) {
		gl_FragColor = vec4(length(P-A)/norm,length(P-B)/norm,length(P-C)/norm,1);
	}
}