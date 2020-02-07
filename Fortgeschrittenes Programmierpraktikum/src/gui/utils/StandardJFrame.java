package gui.utils;

import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class StandardJFrame extends JFrame {

	public StandardJFrame(Dimension dim, int defaultCloseOperation) {
		super();
		this.setSize(dim);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(defaultCloseOperation);
		this.setLayout(new GridBagLayout());
	}
	
	public StandardJFrame(String title, Dimension dim, int defaultCloseOperation) {
		super(title);
		this.setSize(dim);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(defaultCloseOperation);
		this.setLayout(new GridBagLayout());
	}
	
}
