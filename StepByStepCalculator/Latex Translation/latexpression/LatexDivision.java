package latexpression;

import yekocalc.*;

public class LatexDivision extends LatexExpression {
	LatexEvaluatable numerator, denominator;
	
	public LatexDivision(LatexEvaluatable numerator, LatexEvaluatable denominator) {
		this.numerator = numerator;
		this.denominator = denominator;
	}
	
	@Override
	public void accept(LatexExpressionVisitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public float strength() {
		return 2;
	}
	
	public LatexDivision() {
		this(null, null);
	}
	
	public void setNumerator(LatexEvaluatable numer) {
		this.numerator = numer;
	}
	
	public void setDenominator(LatexEvaluatable denom) {
		this.denominator = denom;
	}
	
	@Override
	public String toLatexStr() {
		StringBuilder str = new StringBuilder();
		str.append("\\frac");
		str.append('{');
		str.append(numerator.evaluate().toLatexStr());
		str.append('}');
		str.append('{');
		str.append(denominator.evaluate().toLatexStr());
		str.append('}');
		return str.toString();
	}
	
	@Override
	public Symbol toSymbol(VariableIDTable table) {
		return new Fraction(numerator.evaluate().toSymbol(table), denominator.evaluate().toSymbol(table));
	}

}
