package latexpression;

import java.util.LinkedList;

public abstract class LatexOperation {
	public abstract boolean isOperation(LinkedList<LatexElement> elements);
	public abstract LatexExpression toExpression(LinkedList<LatexElement> elements);
}
