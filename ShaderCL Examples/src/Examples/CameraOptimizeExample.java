package Examples;

import java.awt.image.BufferedImage;
import java.io.File;

import Core.FilterOp;

public class CameraOptimizeExample {
	static TestBed tester; 
	static FilterOp op; 
	public static void main( String[] args ) {
		tester = new TestBed(640,480);
		
		op = new FilterOp("lens");
		
		BufferedImage original = TestBed.loadImage( new File("images\\camera.png") );
		tester.setImage(original);
		
		double dL = 0.1;
		double best = 0;
		double A = 0;
		double B = 0;
		double C = 0;
		for ( double a = 0; a < 0.3; a += dL ) {
			for ( double b = -0.3; b < 0.3; b += dL ) {
				for ( double c = -0.3; c < 0.3; c += dL ) {
					double r = trial(original,a,b,c);
					if ( r > best ) {
						best = r;
						System.out.println(best+" : " + a + ", " + b +", " + c );
						A = a;
						B = b;
						C = c;
					}
				
					op.setFloat("A", a);
					op.setFloat("B", b);
					op.setFloat("C", c);
					op.apply(original);
					tester.setImage(FilterOp.getImage());
					
					try { Thread.sleep(1); } catch ( Exception e ) {}
				}
			}
		}
		
		op.setFloat("A", A);
		op.setFloat("B", B);
		op.setFloat("C", C);
		op.apply(original);
		tester.setImage(FilterOp.getImage());
	}
	
	public static double trial( BufferedImage inp, double a, double b, double c ) {
		op.setFloat("A", a);
		op.setFloat("B", b);
		op.setFloat("C", c);
		op.apply(inp);
		BufferedImage original = FilterOp.getImage();
		
		double rating = 0;
		int[] Ccount = new int[original.getWidth()];
		int[] Rcount = new int[original.getHeight()];
		
		// Populate row and column counts
		for ( int i = 0; i < original.getHeight(); i++ ) {
			for ( int j = 0; j < original.getWidth(); j++ ) {
				int pixel = original.getRGB(j, i);
				if ( (pixel&0x00ffffff)==0x0 ) {
					Rcount[i]++;
					Ccount[j]++;
				}
			}
		}
		
		// Rate by counts
		int THRESH = 100;
		int best = 0;
		float avg_max_col = 0;
		float avg_max_row = 0;
		int Ncol = 0;
		int Nrow = 0;
		boolean low = true;
		
		for ( int i = 0; i < original.getWidth(); i++ ) {
			if ( Ccount[i]>THRESH) {
				if ( low ) {
					low = false;
				}
				if ( Ccount[i]>best ) 
					best = Ccount[i];
			}
			if ( Ccount[i]<=THRESH ) {
				if ( !low ) {
					avg_max_col += best;
					Ncol++;
				}
				low = true;
				best = 0;
			}
		}
		low = true;
		best = 0;
		for ( int i = 0; i < original.getHeight(); i++ ) {
			if ( Rcount[i]>THRESH) {
				if ( low ) {
					low = false;
				}
				if ( Rcount[i]>best ) 
					best = Rcount[i];
			}
			if ( Rcount[i]<=THRESH ) {
				if ( !low ) {
					avg_max_row += best;
					Nrow++;
				}
				low = true;
				best = 0;
			}
		}
		/*
		System.out.println(avg_max_col/Ncol+","+avg_max_row/Nrow);
		System.out.println(Ncol+","+Nrow);
		*/
		rating = avg_max_row/Nrow*avg_max_col/Ncol;
		
		return rating;
	}
}
