package games.utils;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class ConnectFour implements Protocol {

	public GameboardData gameboardData;
	public Point lastSet;

	public ConnectFour(int defaultContent) {
		gameboardData = new GameboardData(7, 6, defaultContent);
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
		pushMove(new Move(new GameboardData(gameboardData.gameboard)));
	}

	public boolean checkWin(Point p) { // p only gives coordinates to ease win-calculation, gameboardData is being set
										// in ActionListener of columnButton
		int column = p.x;
		int row = p.y;
		
		int playerIdentifier = gameboardData.gameboard.get(column).get(row);

		// horizontal
		int leftside = 0;
		int rightside = 0;
		while (column - leftside > 0
				&& gameboardData.gameboard.get(column - leftside - 1).get(row) == playerIdentifier) {
			leftside++;
		}
		while (column + rightside < gameboardData.xDim - 1
				&& gameboardData.gameboard.get(column + rightside + 1).get(row) == playerIdentifier) {
			rightside++;
		}
		// vertical
		int above = 0;
		int below = 0;
		while (row + below < gameboardData.yDim - 1 && gameboardData.gameboard.get(column).get(row + below + 1) == playerIdentifier) {
			below++;
		}
		while (row -  above > 0 && gameboardData.gameboard.get(column).get(row - above -1) == playerIdentifier) {
			above++;
		}
		// left-diagonal
		int left_above = 0;
		int right_below = 0;
		while (column - left_above > 0 && row - left_above > 0 && gameboardData.gameboard.get(column-left_above-1).get(row-left_above-1) == playerIdentifier) {
			left_above++;
		}
		while (column + right_below < gameboardData.xDim - 1 && row + right_below < gameboardData.yDim - 1 && gameboardData.gameboard.get(column+right_below+1).get(row+right_below+1) == playerIdentifier) {
			right_below++;
		}
		// right-diagonal
		int right_above = 0;
		int left_below = 0;
		while (column + right_above < gameboardData.xDim - 1 && row - right_above > 0 && gameboardData.gameboard.get(column+right_above+1).get(row-right_above-1) == playerIdentifier) {
			right_above++;
		}
		while (column - left_below > 0 && row + left_below < gameboardData.yDim - 1 && gameboardData.gameboard.get(column-left_below-1).get(row+left_below+1) == playerIdentifier) {
			left_below++;
		}
//		System.out.println(
//				"horizontal : " + (leftside + 1 + rightside) + "\nvertical : " + (above + 1 + below) + "\ndiagonal1 : "
//						+ (left_above + 1 + right_below) + "\ndiagonal2 : " + (right_above + 1 + left_below));
		if ((leftside + 1 + rightside) > 3 || (above + 1 + below) > 3 || (left_above + 1 + right_below) > 3
				|| (right_above + 1 + left_below) > 3) {
			return true;
		}

		return false;
	}

	public void playerMove(int column) {
		for (int i = gameboardData.yDim - 1; i >= 0; i--) {
			if (gameboardData.gameboard.get(column).get(i) == 0) {
				gameboardData.gameboard.get(column).set(i, 1);
				GameboardData gb = new GameboardData(gameboardData.gameboard);
				Move move = new Move(gb);
				pushMove(move);
				lastSet = new Point(column, i);
				break;
			}
		}

	}

	public void pcMove() {
		Random rand = new Random();
		int column = rand.nextInt(7);
		while (gameboardData.gameboard.get(column).get(0) != 0) {
			column = rand.nextInt(7);
		}
		for (int i = gameboardData.yDim - 1; i >= 0; i--) {
			if (gameboardData.gameboard.get(column).get(i) == 0) {
				gameboardData.gameboard.get(column).set(i, 2);
				GameboardData gb = new GameboardData(gameboardData.gameboard);
				Move move = new Move(gb);
				pushMove(move);
				lastSet = new Point(column, i);
				break;
			}
		}
	}

	public void printGameboard(ArrayList<ArrayList<Integer>> gb) {
		for (int i = 0; i < gameboardData.yDim; i++) {
			for (int j = 0; j < gameboardData.xDim; j++) {
				System.out.print(gb.get(j).get(i));
			}
			System.out.println("");
		}
		System.out.println("");
	}

}
