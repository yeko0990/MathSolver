package latexpression;

import java.util.LinkedList;

public class LatexExtractAddition extends LatexExtractOperator {
	LatexAddition add;
	
	@Override
	protected Operator op() {
		return Operator.Add;
	}

	@Override
	protected void createExpression() {
		add = new LatexAddition();
	}
	
	@Override
	protected LatexExpression expression() {
		return add;
	}

	@Override
	protected void addParameter(LatexEvaluatable param) {
		add.addParam(param);
	}
	
	@Override
	public boolean isOperation(LinkedList<LatexElement> elements) {
		int nesting = 0;
		LatexElement next;
		for (int i = 0; i < elements.size(); i++) {
			next = elements.get(i);
			nesting += next.isNesting() ? 1 : 0;
			nesting -= next.isDoneNesting() ? 1 : 0;
			if (next.isOperator(Operator.Minus) && nesting == 0 && i!=0) return true; ///MINUS IS ALSO AN ADDITION!
																		///We are checking it is not i=0 because a minus
																		///as the first element is not an addition.
																		///Examples: -5x, -3, -(x+2)...
			if (next.isOperator(this.op()) && nesting == 0) return true;
		}
		return false;
	}

}
