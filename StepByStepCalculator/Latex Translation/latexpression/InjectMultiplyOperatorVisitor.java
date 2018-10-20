package latexpression;

public class InjectMultiplyOperatorVisitor implements LatexExpressionVisitor {
	private boolean isMinusBeforeExpression;
	
	private boolean shouldInject;
	
	public InjectMultiplyOperatorVisitor() {
		
	}
	
	///isFirstParam was created to understand if an operator shall be injected before -1
	public boolean shouldInjectBeforeExpression(LatexExpression expression, boolean isMinusBeforeExpression) {
		this.isMinusBeforeExpression = isMinusBeforeExpression;
		expression.accept(this);
		return shouldInject;
	}
	
	private void injectIfNotAfterMinus() {
		shouldInject = !isMinusBeforeExpression;
	}

	@Override
	public void visit(LatexAddition exp) {
		shouldInject = false;
	}

	@Override
	public void visit(LatexMultiply exp) {
		shouldInject = false;
	}

	@Override
	public void visit(LatexDivision exp) {
		shouldInject = false;
	}

	@Override
	public void visit(LatexNumber exp) {
		injectIfNotAfterMinus();
	}

	@Override
	public void visit(LatexVariable exp) {
		shouldInject = false;
	}

	///Should place a multiply according to the value of the base
	@Override
	public void visit(LatexPower exp) {
		shouldInject = (new InjectMultiplyOperatorVisitor()).shouldInjectBeforeExpression(exp.base.evaluate(),
																							isMinusBeforeExpression);
	}

	@Override
	public void visit(LatexRoot exp) {
		injectIfNotAfterMinus();
	}

	@Override
	public void visit(LatexPlusMinus exp) {
		shouldInject = false;
	}
}
