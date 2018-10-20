package latexpression;

import java.util.LinkedList;

public class LatexGetVariable extends LatexOperation {

	public LatexGetVariable() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isOperation(LinkedList<LatexElement> elements) {
		if (elements.size() != 1) return false;
		return elements.get(0).isVariable();
	}

	@Override
	public LatexExpression toExpression(LinkedList<LatexElement> elements) {
		if (!isOperation(elements)) return null;
		VarElementContext context = new VarElementContext();
		elements.get(0).getVariable(context);
		return new LatexVariable(context.name);
	}

}
