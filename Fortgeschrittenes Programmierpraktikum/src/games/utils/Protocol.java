package games.utils;

import java.util.Stack;

public interface Protocol {
	public Stack<Move> moveStack = new Stack<Move>();
	public abstract void pushMove(Move move);
	public abstract void popMove();
}
