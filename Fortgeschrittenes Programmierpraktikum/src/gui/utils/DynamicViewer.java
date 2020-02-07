package gui.utils;

import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class DynamicViewer {

	private Dimension dim;
	private Container mainContainer;
	private JTextArea list = new JTextArea();
	private String listText = list.getText();
	private JScrollPane viewer = new JScrollPane(list);

	public DynamicViewer(Dimension d) {
		dim = d;
		mainContainer = new JPanel();
		mainContainer.setMinimumSize(dim);
		mainContainer.setPreferredSize(dim);
		list.setEditable(false);
		list.setMinimumSize(new Dimension(dim.width - 50, dim.height - 50));
		viewer.setPreferredSize(new Dimension(dim.width - 10, dim.height - 10));
		viewer.setMinimumSize(new Dimension(dim.width - 10, dim.height - 10));
		viewer.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		viewer.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		mainContainer.add(viewer);
	}

	public Container getContainer() {
		return mainContainer;
	}

	public void addEntry(String entry) {
		list.append(entry + "\n");
		listText = list.getText();
	}
	
	public void removeEntry(String entry) {
		if(listText.contains(entry)) {
			list.setText(listText.replace(entry + "\n", ""));
			listText = list.getText();
		}
	}
}
