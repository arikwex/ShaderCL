package Examples;

import java.awt.image.BufferedImage;
import java.io.File;

import Core.FilterOp;

public class MultipleSizesExample {
	public static void main( String[] args ) {
		TestBed tester = new TestBed(700,700);
		
		FilterOp op = new FilterOp("blur");
		FilterOp op2 = new FilterOp("edge");
		
		BufferedImage img1 = TestBed.loadImage( new File("images\\lena.png") );
		BufferedImage img2 = TestBed.loadImage( new File("images\\rainbow.png") );
		
		while ( true ) {
			op.apply(img1);
			op2.apply();
			BufferedImage result1 = FilterOp.getImage();
			tester.setImage(result1);
			
			try { Thread.sleep(500); } catch ( Exception e ) {}
			
			op2.apply(img2);
			op.apply();
			BufferedImage result2 = FilterOp.getImage();
			tester.setImage(result2);
			
			try { Thread.sleep(500); } catch ( Exception e ) {}
		}
	}
}
