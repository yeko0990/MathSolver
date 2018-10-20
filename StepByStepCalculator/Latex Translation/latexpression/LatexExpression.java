package latexpression;
import java.util.*;
import yekocalc.Symbol;
import yekocalc.CalcNumber;

public abstract class LatexExpression implements LatexEvaluatable {
	public LatexExpression() {
		
	}
	
	public abstract void accept(LatexExpressionVisitor visitor);

	public abstract float strength(); ///Strength of order of evaluation - the BIGGEST strength is first, SMALLEST is last.

	public abstract String toLatexStr();
	public abstract Symbol toSymbol(VariableIDTable id_list);
	
	
	///A MULTIPLY-STRENGTH is relative to the priority of the expression in a multiplication.
	///higher strength expressions are shown first in a multiplication.
	public float getMultiplyStrength() {
		return 1;
	}
	
	@Override
	public final LatexExpression evaluate() {
		return this;
	}

}
