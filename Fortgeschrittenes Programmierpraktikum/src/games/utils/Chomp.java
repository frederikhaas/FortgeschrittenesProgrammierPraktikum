package games.utils;

import java.util.ArrayList;
import java.util.Random;

public class Chomp implements Protocol {

	public GameboardData gameboardData;
	public Move lastMove;
	public int xDim;
	public int yDim;

	public Chomp(int xdim, int ydim, int defaultContent) {
		xDim = xdim;
		yDim = ydim;
		gameboardData = new GameboardData(xdim, ydim, defaultContent);
		initializeGameboardAndMovestack();
	}

	@Override
	public void pushMove(Move move) {
		moveStack.push(move);
	}

	@Override
	public void popMove() {
		moveStack.pop();
		gameboardData.gameboard = new GameboardData(moveStack.peek().gameboardData.gameboard).gameboard;
	}

	public void initializeGameboardAndMovestack() {
		gameboardData.gameboard.get(0).set(0, 1);
		GameboardData gbd = new GameboardData(gameboardData.gameboard);
		Move move = new Move(gbd);
		moveStack.push(move);
	}
	
	public boolean checkWin(Move move) {
		GameboardData gbd = move.gameboardData;
		ArrayList<ArrayList<Integer>> gb = gbd.gameboard;
		boolean win = true;
		for (int i = 0; i < gb.size(); i++) {
			for (int j = 0; j < gb.get(i).size(); j++) {
				if (gb.get(i).get(j).equals(0)) {
					win = false;
				}
			}
		}
		return win;
	}

	public void pcMove() {
		int x;
		int y;
		Random randx = new Random();
		Random randy = new Random();
		x = randx.nextInt(gameboardData.xDim);
		y = randy.nextInt(gameboardData.yDim);
		while(gameboardData.gameboard.get(x).get(y) != 0) {
			x = randx.nextInt(gameboardData.xDim);
			y = randy.nextInt(gameboardData.yDim);
		}
		for (int i = x; i < gameboardData.xDim; i++) {
			for (int j = y; j < gameboardData.yDim; j++) {
				gameboardData.gameboard.get(i).set(j, 1);
			}
		}
		GameboardData gbd = new GameboardData(gameboardData.gameboard);
		Move move = new Move(gbd);
		lastMove = move;
		pushMove(move);
	}
	
	public void printGameboard(ArrayList<ArrayList<Integer>> gb) {
		for (int i = 0; i < gameboardData.yDim; i++) {
			for (int j = 0; j < gameboardData.xDim; j++) {
				System.out.print(gb.get(j).get(i));
			}
			System.out.println("");
		}
	}

}
