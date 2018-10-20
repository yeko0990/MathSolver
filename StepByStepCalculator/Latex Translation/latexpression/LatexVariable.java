package latexpression;

import java.util.HashMap;

import yekocalc.CalcNumber;
import yekocalc.Symbol;
import yekocalc.Variable;

public class LatexVariable extends LatexExpression {
	private final String var;
	
	public LatexVariable(String var) {
		this.var = var;
	}
	
	@Override
	public void accept(LatexExpressionVisitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public float strength() {
		return 5;
	}

	@Override
	public float getMultiplyStrength() {
		return 2;
	}
	
	@Override
	public String toLatexStr() {
		return var;
	}

	@Override
	public Symbol toSymbol(VariableIDTable idList) {
		return new Variable(idList.getID(var));
	}

}
