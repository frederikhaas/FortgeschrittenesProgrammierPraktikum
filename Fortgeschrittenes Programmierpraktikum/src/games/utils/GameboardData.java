package games.utils;

import java.util.ArrayList;

public class GameboardData {
	public int xDim;
	public int yDim;
	public ArrayList<ArrayList<Integer>> gameboard = new ArrayList<ArrayList<Integer>>();

	public GameboardData(int x, int y, int defaultContent) {
		xDim = x;
		yDim = y;
		gameboard = new  ArrayList<ArrayList<Integer>>();
		for(int i = 0; i < xDim; i++) {
			gameboard.add(new ArrayList<Integer>());
			for(int j = 0; j < yDim; j++) {
				gameboard.get(i).add(defaultContent);
			}
		}
	}
	
	public GameboardData(ArrayList<ArrayList<Integer>> Gameboard) {
		xDim = Gameboard.size();
		yDim = Gameboard.get(0).size();
		for (int i = 0; i < Gameboard.size(); i++) {
			gameboard.add(new ArrayList<Integer>(Gameboard.get(i)));
		}
	}
	
	public String transformGameboard() {
		String rep = "";
		for (int i = 0; i < xDim; i++) {
			for (int j = 0; j < yDim; j++) {
				rep = rep + String.valueOf(gameboard.get(i).get(j)) + " ";
			}
		}
		return rep.trim();
	}
	
}
