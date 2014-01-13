varying float x;
varying float y;
uniform float select_hue = 0.0;
uniform float window_cos = -10.0; 
uniform sampler2D txtr;

float min( float a, float b, float c ) {
	if ( a > b ) {
		if ( a > c ) {
			return a;
		} else if ( c > a ) {
			return c;
		} else {
			return a;
		}
	} else {
		if ( b > c ) {
			return b;
		} else if ( c > b ) {
			return c;
		} else {
			return b;
		}
	}
}

float max( float a, float b, float c ) {
	return -min(-a,-b,-c);
}

void main() {
	vec4 color = texture(txtr,vec2(x,y),0.0);
	gl_FragColor = vec4(0,0,0,1);
	
	float MIN, MAX, delta;
	float r = color.z;
	float g = color.y;
	float b = color.x;

	MIN = min( r, g, b );
	MAX = max( r, g, b );
	
	float v = MAX;
	float s = 0;
	float h = 0;

	delta = MAX - MIN;

	if( MAX != 0 ) {
		s = delta / MAX;		// s
	} else {
		// r = g = b = 0		// s = 0, v is undefined
		s = 0;
		h = 0;
	}

	if( r == MAX ) {
		h = ( g - b ) / delta;		// between yellow & magenta
	} else if( g == MAX ) {
		h = 2.0 + ( b - r ) / delta;	// between cyan & yellow
	} else {
		h = 4.0 + ( r - g ) / delta;	// between magenta & cyan
	}

	if( h < 0 )
		h += 6.0;
	
	if ( cos(h*2.0*3.14159/6.0)*cos(select_hue*2.0*3.14159/360.0) +
		 sin(h*2.0*3.14159/6.0)*sin(select_hue*2.0*3.14159/360.0) > window_cos ) {
		gl_FragColor = vec4(b,g,r,1);
	} else {
		gl_FragColor = vec4(0,0,0,1);
	}
}