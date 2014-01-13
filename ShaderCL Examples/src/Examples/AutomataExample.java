package Examples;

import java.awt.image.BufferedImage;
import java.io.File;

import Core.FilterOp;

public class AutomataExample {
	public static void main( String[] args ) {
		TestBed tester = new TestBed(640,480);
		
		FilterOp op = new FilterOp("automata");
		
		BufferedImage state = TestBed.loadImage( new File("images\\automata.png") );

		int gen = 0;
		int steps = 20;
		double fps = 60;
		
		op.apply(state);
		while ( true ) {
			tester.setImage(state);
			long S = System.currentTimeMillis();
			for ( int i = 0; i < steps; i++ )
				op.apply();
			state = FilterOp.getImage();
			long curr = System.currentTimeMillis();
			
			gen+=steps;
			fps = 0.9*fps + 1000.0/(curr-S)*0.1;
			tester.setText("FPS: " + (int)fps +", GENERATION: " + gen);
		}
	}
}
