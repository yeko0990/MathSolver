package latexpression;

import yekocalc.Root;
import yekocalc.Symbol;

public class LatexRoot extends LatexExpression {
	private LatexEvaluatable base, rootBy;
	
	public LatexRoot(LatexExpression base, LatexExpression rootBy) {
		this.base = base;
		this.rootBy = rootBy;
	}

	@Override
	public void accept(LatexExpressionVisitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public float strength() {
		return 4;
	}
	
	@Override 
	public float getMultiplyStrength() {
		return 1.5f;
	}
	
	private boolean isRootByEqualsTwo(String rootByStr) {
		return rootByStr.equals("2");
	}

	@Override
	public String toLatexStr() {
		StringBuilder str = new StringBuilder();
		str.append("\\sqrt");
		
		String rootByStr = rootBy.evaluate().toLatexStr();
		String baseStr = base.evaluate().toLatexStr();
		
		if (!isRootByEqualsTwo(rootByStr)) {
			str.append('[');
			str.append(rootByStr);
			str.append(']');
		}
		str.append('{');
		str.append(baseStr);
		str.append('}');
		return str.toString();
	}

	@Override
	public Symbol toSymbol(VariableIDTable id_list) {
		return new Root(this.base.evaluate().toSymbol(id_list), this.rootBy.evaluate().toSymbol(id_list));
	}

}
