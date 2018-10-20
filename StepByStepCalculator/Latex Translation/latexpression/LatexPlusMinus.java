package latexpression;

import yekocalc.PlusMinus;
import yekocalc.Symbol;

public class LatexPlusMinus extends LatexExpression {
	private LatexEvaluatable parameter;
	
	public LatexPlusMinus(LatexEvaluatable parameter) {
		this.parameter = parameter;
	}

	@Override
	public void accept(LatexExpressionVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public float strength() {
		return 1.5f;
	}
	
	@Override
	public float getMultiplyStrength() {
		return 10f;
	}

	private boolean shouldNestParameter() {
		return parameter.evaluate().strength() <= this.strength();
	}
	
	@Override
	public String toLatexStr() {
		StringBuilder build = new StringBuilder();
		boolean shouldNest = this.shouldNestParameter();
		if (shouldNest) build.append("(");
		build.append("\\pm");
		build.append(parameter.evaluate().toLatexStr());
		if (shouldNest) build.append(")");
		return build.toString();
	}

	@Override
	public Symbol toSymbol(VariableIDTable id_list) {
		return new PlusMinus(this.parameter.evaluate().toSymbol(id_list));
	}

}
