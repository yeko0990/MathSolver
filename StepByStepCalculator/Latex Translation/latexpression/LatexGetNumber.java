package latexpression;

import java.util.LinkedList;

import yekocalc.CalcNumber;

public class LatexGetNumber extends LatexOperation {

	public LatexGetNumber() {
		
	}

	@Override
	public boolean isOperation(LinkedList<LatexElement> elements) {
		if (elements.size() == 1) return elements.get(0).isNumber();
		if (elements.size() == 2) return elements.get(0).isOperator(Operator.Minus) && elements.get(1).isNumber();
		return false;
	}

	@Override
	public LatexExpression toExpression(LinkedList<LatexElement> elements) {
		if (!isOperation(elements)) return null;
		NumberElementContext cont = new NumberElementContext();
		if (elements.size() == 1) elements.get(0).getNumber(cont);
		
		else if (elements.size() == 2 && elements.get(0).isOperator(Operator.Minus)) {
			elements.get(1).getNumber(cont);
			cont.num = cont.num.mul(new CalcNumber(-1));
		}
		
		return new LatexNumber(cont.num);
	}

}
