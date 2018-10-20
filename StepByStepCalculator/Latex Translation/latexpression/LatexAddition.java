package latexpression;
import java.util.*;
import yekocalc.Symbol;
import yekocalc.Addition;

public class LatexAddition extends LatexExpression {
	private final String ADD = "+";
	private List<LatexEvaluatable> params;
	public LatexAddition() {
		params = new LinkedList<LatexEvaluatable>();
	}
	
	@Override
	public void accept(LatexExpressionVisitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public float strength() {
		return 1;
	}
	
	public LatexAddition(List<Symbol> paramsSyms, VariableIDTable idList) {
		this();
		for (Symbol nextP : paramsSyms) params.add(nextP.latex(idList));
	}

	public void addParam(LatexEvaluatable evaluate) {
		params.add(evaluate);
	}
	
	@Override
	public String toLatexStr() {
		LatexAdditionParameters latParams = new LatexAdditionParameters(this.params, this.ADD, this.strength());
		return latParams.toLatexStr();
	}

	@Override
	public Symbol toSymbol(VariableIDTable table) {
		List<Symbol> paramsSyms = new LinkedList<Symbol>();
		for (LatexEvaluatable next : params)
			paramsSyms.add(next.evaluate().toSymbol(table));
		return new Addition(paramsSyms);
	}

}
