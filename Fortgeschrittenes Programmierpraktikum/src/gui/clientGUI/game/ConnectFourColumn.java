package gui.clientGUI.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import gui.utils.GeoShape;

public class ConnectFourColumn {

	JPanel container = new JPanel();
	JButton columnButton = new JButton();
	JPanel columnPanel = new JPanel();
	ArrayList<GeoShape> slotList = new ArrayList<GeoShape>();
	int height; // slots
	int width; // pixels

	public ConnectFourColumn(int Height, int Width) {
		height = Height;
		width = Width;

		container.setPreferredSize(new Dimension(width + 20, (width + 10) * (height + 1) + 20));
		container.setLayout(new GridBagLayout());
		container.setBackground(Color.BLACK);

		columnPanel.setPreferredSize(new Dimension(width + 10, (width + 10) * height));
		columnPanel.setLayout(new GridBagLayout());
		GridBagConstraints columnGBC = new GridBagConstraints();
		columnGBC.insets = new Insets(5, 5, 5, 5);
		for (int i = 0; i < height; i++) {
			GeoShape tokenSlot = new GeoShape();
			tokenSlot.initializeShape(width, width, GeoShape.OVAL, new Color(180, 180, 180), true);
			slotList.add(tokenSlot);
			columnGBC.gridy = i;
			columnPanel.add(tokenSlot, columnGBC);
		}

		columnButton.setMinimumSize(new Dimension(width, width));
		columnButton.setPreferredSize(new Dimension(width, width));

		GridBagConstraints containerGBC = new GridBagConstraints();

		containerGBC.insets = new Insets(5, 5, 5, 5);
		container.add(columnPanel, containerGBC);

		containerGBC.gridy = 1;
		container.add(columnButton, containerGBC);
	}
}
