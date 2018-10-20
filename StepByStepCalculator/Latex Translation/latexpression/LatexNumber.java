package latexpression;

import yekocalc.CalcNumber;
import yekocalc.Symbol;
import yekocalc.NumberSym;
import yekocalc.VariableIDTable;

public class LatexNumber extends LatexExpression {
	private CalcNumber num;
	
	public LatexNumber(CalcNumber num) {
		this.num = num;
	}
	
	@Override
	public void accept(LatexExpressionVisitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public float strength() {
		if (num.isNegative()) return 3.5f; //NOT NEEDED- In LatexParameters class we check if parameter starts with '-'. If
										  //it does than we nest regardless of the parameter strnegth.
		return 6;
	}

	@Override
	public float getMultiplyStrength() {
		if (num.equals(new CalcNumber(-1))) return 4;
		else return 3;
	}
	
	@Override
	public String toLatexStr() {
		return num.toString();
	}

	@Override
	public Symbol toSymbol(VariableIDTable id_list) {
		return new NumberSym(this.num);
	}

}
