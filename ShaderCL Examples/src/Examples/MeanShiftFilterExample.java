package Examples;

import java.awt.image.BufferedImage;
import java.io.File;

import Core.FilterOp;

public class MeanShiftFilterExample {
	public static void main( String[] args ) {
		TestBed tester = new TestBed(640,480);
		
		FilterOp blur = new FilterOp("blur");
		FilterOp meanshift = new FilterOp("meanshift");
		
		BufferedImage original = TestBed.loadImage( new File("images\\rainbow.png") );
		tester.setImage(original);

		blur.setInt("kernel_size", 5);
		meanshift.setInt("neighborhood_size", 6);
		meanshift.setFloat("kernel_C",30.0);
		
		blur.apply(original);
		meanshift.apply();
		tester.setImage(FilterOp.getImage());
	}
}
