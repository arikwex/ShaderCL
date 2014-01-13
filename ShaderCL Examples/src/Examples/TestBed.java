package Examples;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TestBed {
	private int width,height;
	private JFrame win;
	private ViewPanel vp;
	
	public TestBed( int W, int H ) {
		this.width = W+100;
		this.height = H+120;
		
		win = new JFrame("MaslabGL TestBed");
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		vp = new ViewPanel();
		win.getContentPane().add(vp);
		win.setResizable(false);
		win.setSize(width,height);
		win.setVisible(true);
	}
	
	public void setImage( BufferedImage bi ) { vp.setImage(bi); }
	public void setText( String txt ) { vp.setText(txt); }
	
	public class ViewPanel extends JPanel {
		private BufferedImage bi;
		private String text;
		public ViewPanel() {
			this.text = "";
			this.bi = null;
		}
		
		public void setImage( BufferedImage bi ) {
			if ( this.bi==null )
				this.bi = bi;
			else {
				synchronized (this.bi){
					this.bi = bi;
				}
			}
			repaint();
		}
		
		public void setText( String txt ) {
			this.text = txt;
			repaint();
		}
		
		public void paintComponent( Graphics g ) {
			g.setColor(Color.black);
			g.fillRect(0,0,width,height);
			if ( bi!=null ) {
				synchronized (this.bi){
					g.drawImage(bi,50,50,null);
				}
			}
			g.setColor(Color.green);
			g.drawString(text,15,15);
		}
	}
	
	static public BufferedImage loadImage( File f ) {
		try {
			BufferedImage bi = ImageIO.read( f );
			return bi;
		} catch ( Exception e ){}
		return null;
	}
}
