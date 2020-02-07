package gui.utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GeoShape extends JPanel {

	public static final int OVAL = 0;
	public static final int RECT = 1;
	
	public int shape;
	public int width;
	public int height;
	public Color color;
	public boolean filled;

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(color);
		if (filled) {				//draws filled shape
			switch (shape) {
			case OVAL:
				g.fillOval(0, 0, width, height);
				break;
			}
		} else {					//draws outline of shape
			switch(shape) {
			case OVAL:
				g.drawOval(0, 0, width, height);
				break;
			}
		}
	}
	
	public void initializeShape(int Width, int Height, int Shape, Color Color, boolean Filled) {
		width = Width;
		height = Height;
		shape = Shape;
		color = Color;
		filled = Filled;
		this.setPreferredSize(new Dimension(width, height));
//		this.setBounds(new Rectangle(new Dimension(width, height)));
	}

}
