package Examples;

import java.awt.image.BufferedImage;

import Core.FilterOp;

public class FloatArrayExample {
	public static void main( String[] args ) {
		TestBed tester = new TestBed(640,480);
		
		FilterOp op = new FilterOp("triangle");
		
		// Empty image of proper size.
		// A triangle will be rendered on to this
		BufferedImage original = new BufferedImage(640,480,BufferedImage.TYPE_INT_ARGB);
		
		int W = 640;
		int H = 480;
		float angle = 0;
		float size = 150f;
		op.apply(original);
		while ( true ) {
			float[] pts = new float[]{
					(float)Math.cos(angle)*size+W/2,(float)Math.sin(angle)*size+H/2,
					(float)Math.cos(angle+Math.PI*2.0/3.0)*size+W/2,(float)Math.sin(angle+Math.PI*2.0/3.0)*size+H/2,
					(float)Math.cos(angle+Math.PI*4.0/3.0)*size+W/2,(float)Math.sin(angle+Math.PI*4.0/3.0)*size+H/2
			};
			op.setFloatArray("pts", pts);
			op.apply();
			tester.setImage(FilterOp.getImage());
			
			angle += 0.005f;
		}
	}
}