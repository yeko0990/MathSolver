package latexpression;

public class LatexExtractDivision extends LatexExtractOperator {
	private LatexDivision exp;
	private boolean numeratorSet;
	
	public LatexExtractDivision() {
		numeratorSet = false;
	}

	@Override
	protected Operator op() {
		return Operator.Div;
	}

	@Override
	protected void createExpression() {
		exp =  new LatexDivision();

	}

	@Override
	protected void addParameter(LatexEvaluatable param) {
		if (numeratorSet) exp.setDenominator(param);
		else {
			exp.setNumerator(param);
			numeratorSet = true;
		}

	}

	@Override
	protected LatexExpression expression() {
		return this.exp;
	}

}
