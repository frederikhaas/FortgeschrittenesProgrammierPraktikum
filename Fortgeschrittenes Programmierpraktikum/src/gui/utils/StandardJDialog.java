package gui.utils;

import java.awt.Dimension;
import java.awt.LayoutManager;

import javax.swing.JDialog;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class StandardJDialog extends JDialog {

	public StandardJDialog(Dimension dim, LayoutManager layout) {
		super();
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setModalityType(JDialog.DEFAULT_MODALITY_TYPE);
		this.setSize(dim);
		this.setResizable(false);
		this.setLayout(layout);
		this.setLocationRelativeTo(null);
	}
}
