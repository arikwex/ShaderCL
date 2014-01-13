package Examples;

import java.awt.image.BufferedImage;
import java.io.File;

import Core.FilterOp;

public class EdgeDetectionExample {
	public static void main( String[] args ) {
		TestBed tester = new TestBed(512,512);
		
		FilterOp blur = new FilterOp("blur");
		FilterOp edge = new FilterOp("edge");
		
		BufferedImage original = TestBed.loadImage( new File("images\\lena.png") );
		
		double fps = 100;
		while ( true ) {
			long start = System.currentTimeMillis();
			blur.apply(original);
			edge.apply();
			BufferedImage filtered = FilterOp.getImage();
			long end = System.currentTimeMillis();
			fps = fps*0.95 + 1000.0/(end-start)*0.05;
			tester.setText("FPS : " + (int)fps);
			tester.setImage(filtered);
		}
	}
}
