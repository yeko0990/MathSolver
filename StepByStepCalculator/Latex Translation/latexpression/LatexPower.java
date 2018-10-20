package latexpression;

import yekocalc.*;

public class LatexPower extends LatexExpression {
	private final String POW = "^";
	LatexEvaluatable base, exponent;
	public LatexPower(LatexEvaluatable base, LatexEvaluatable exponent) {
		this.base = base;
		this.exponent = exponent;
	}

	public LatexPower() {
		this(null, null);
	}
	
	@Override
	public void accept(LatexExpressionVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public float strength() {
		return 4;
	}
	
	public void setBase(LatexEvaluatable base) {
		this.base = base;
	}
	
	public void setExponent(LatexEvaluatable exp) {
		this.exponent = exp;
	}
	
	@Override
	public String toLatexStr() {
		//LatexParameters params = new LatexParameters(ListUtils.toLinkedList(base, exponent), this.POW, this.strength);
		//return params.toLatexStr();
		StringBuilder str = new StringBuilder();
		boolean shouldNest = base.evaluate().strength() <= this.strength();
		if (shouldNest) str.append("(");
		str.append(base.evaluate().toLatexStr());
		if (shouldNest) str.append(")");
		
		str.append(POW);
		
		str.append("{");
		str.append(exponent.evaluate().toLatexStr());
		str.append("}");
		return str.toString();
	}
	
	@Override
	public Symbol toSymbol(VariableIDTable table) {
		return new Power(base.evaluate().toSymbol(table), exponent.evaluate().toSymbol(table));
	}

}
