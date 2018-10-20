package latexpression;

import java.util.LinkedList;

public class LatexExtractPower extends LatexExtractOperator {
	LatexPower pow;
	boolean setBase;
	
	public LatexExtractPower() {
		setBase = false;
	}

	@Override
	protected Operator op() {
		return Operator.Pow;
	}
	
	@Override
	public void createExpression() {
		pow = new LatexPower();
	}
	
	@Override
	public void addParameter(LatexEvaluatable param) {
		if (setBase) pow.setExponent(param);
		else {
			pow.setBase(param);
			setBase = true;
		}
	}
	
	@Override
	public LatexExpression expression() {
		return this.pow;
	}

}
