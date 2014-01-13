package Examples;

import java.awt.image.BufferedImage;
import java.io.File;

import Core.FilterOp;

public class GaussianFilterExample {
	public static void main( String[] args ) {
		TestBed tester = new TestBed(512,512);
		
		FilterOp op = new FilterOp("blur");
		
		BufferedImage original = TestBed.loadImage( new File("images\\lena.png") );
		tester.setImage(original);
		
		op.setInt("kernel_size",5);
		op.apply(original);
		while ( true ) {
			tester.setImage(FilterOp.getImage());
			op.apply();
		}
	}
}
