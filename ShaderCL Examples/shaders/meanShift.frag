varying float x;
varying float y;
uniform float width;
uniform float height;

uniform int neighborhood_size = 4;
uniform float kernel_C = 12.5;

uniform sampler2D txtr;

void main() {
	float dx = 1.0/width;
	float dy = 1.0/height;
	gl_FragColor = vec4(0,0,0,1);
	
	vec2 pos = vec2(x,y);
	vec2 prev = vec2(-100,-100);
	
	int sz = neighborhood_size;
	int j = 0;
	while ( j < 100 ) {
		vec2 donom = vec2(0,0);
		float numer = 0;
		
		vec4 col = texture(txtr,pos,0.0);
		
		for ( int i = -sz; i <= sz; i++ ) {
			for ( int j = -sz; j <= sz; j++ ) {
				vec2 neighbor = vec2(pos.x+float(i)*dx,pos.y+float(j)*dy);
				vec4 sample = texture(txtr,neighbor,0.0);
				float K = pow(2.0,-kernel_C*length(sample-col));
				donom += K*neighbor;
				numer += K;
			}
		}
		
		prev = vec2(pos.xy);
		pos = donom/numer;
		
		j++;
	}
	
	vec4 col = texture(txtr,pos,0.0);
	gl_FragColor = col;
	
	float dist = length(cross(vec3(1,1,1),-col.xyz))/length(vec3(1,1,1));
	if ( dist < 0.07 && length(col.xyz)>3 )
		gl_FragColor = vec4(1,1,1,1);
}