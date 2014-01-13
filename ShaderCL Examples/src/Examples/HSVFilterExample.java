package Examples;

import java.awt.image.BufferedImage;
import java.io.File;

import Core.FilterOp;

public class HSVFilterExample {
	public static void main( String[] args ) {
		TestBed tester = new TestBed(640,480);
		
		FilterOp op = new FilterOp("blur");
		FilterOp op2 = new FilterOp("hue_window");
		
		BufferedImage original = TestBed.loadImage( new File("images\\rainbow.png") );
		tester.setImage(original);
		
		op2.setFloat("window_cos", 0.9);
		
		float hue = 0;
		while ( true ) {
			hue += 1;
			op2.setFloat("select_hue", hue);
			
			op.apply(original);
			op2.apply();
			tester.setImage(FilterOp.getImage());
		}
	}
}