package latexpression;
import yekocalc.Equation;
import yekocalc.Expression;

public class LatexEquationUserString {
	private final String str;
	
	public LatexEquationUserString(String str) {
		this.str = str;
	}
	
	public Equation getEquation(VariableIDDynamicTable manager) throws IllegalLatexStringException {
		String[] sides = str.split("=");
		if (sides.length != 2) throw new IllegalLatexStringException();
		LatexUserString left, right;
		left = new LatexUserString(sides[0]);
		right = new LatexUserString(sides[1]);
		
		Expression leftExp = new Expression(left.toExpression().toSymbol(manager));
		Expression rightExp = new Expression(right.toExpression().toSymbol(manager));
		Equation eq = new Equation(leftExp, rightExp);
		return eq;
	}

}
