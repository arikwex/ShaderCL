varying float x;
varying float y;
uniform float width;
uniform float height;
uniform sampler2D txtr;

/*
    Any live cell with fewer than two live neighbours dies, as if caused by under-population.
    Any live cell with two or three live neighbours lives on to the next generation.
    Any live cell with more than three live neighbours dies, as if by overcrowding.
    Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
*/

void main() {
	float dx = 1.0/width;
	float dy = 1.0/height;
	
	int self = texture(txtr,vec2(x,y),0.0).x>0.5?1:0;
	int n0 = texture(txtr,vec2(x-dx,y-dy),0.0).x>0.5?1:0;
	int n1 = texture(txtr,vec2(x,y-dy),0.0).x>0.5?1:0;
	int n2 = texture(txtr,vec2(x+dx,y-dy),0.0).x>0.5?1:0;
	int n3 = texture(txtr,vec2(x-dx,y),0.0).x>0.5?1:0;
	int n4 = texture(txtr,vec2(x+dx,y),0.0).x>0.5?1:0;
	int n5 = texture(txtr,vec2(x-dx,y+dy),0.0).x>0.5?1:0;
	int n6 = texture(txtr,vec2(x,y+dy),0.0).x>0.5?1:0;
	int n7 = texture(txtr,vec2(x+dx,y+dy),0.0).x>0.5?1:0;
	
	int n = n0+n1+n2+n3+n4+n5+n6+n7;
	if ( self==1 && n < 2 ) {
		gl_FragColor = vec4(0,0,0,1);
	} else if ( self==1 && (n==2 || n==3) ) {
		gl_FragColor = vec4(1,1,1,1);
	} else if ( self==1 && n>3 ) {
		gl_FragColor = vec4(0,0,0,1);
	} else if ( self==0 && n==3 ) {
		gl_FragColor = vec4(1,1,1,1);
	}
}