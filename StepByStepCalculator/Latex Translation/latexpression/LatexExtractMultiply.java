package latexpression;

public class LatexExtractMultiply extends LatexExtractOperator {
	LatexMultiply multi;

	@Override
	protected Operator op() {
		return Operator.Mul;
	}

	@Override
	protected void createExpression() {
		multi = new LatexMultiply();

	}

	@Override
	protected void addParameter(LatexEvaluatable param) {
		multi.addParam(param);

	}

	@Override
	protected LatexExpression expression() {
		return multi;
	}

}
