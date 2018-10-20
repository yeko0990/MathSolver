package latexpression;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import yekocalc.Multiplication;
import yekocalc.Symbol;

public class LatexMultiply extends LatexExpression {
	private final String MULTI = " \\cdot ";
	LinkedList<LatexEvaluatable> params;
	
	public LatexMultiply() {
		params = new LinkedList<LatexEvaluatable>();
	}
	
	@Override
	public void accept(LatexExpressionVisitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public float strength() {
		return 2;
	}
	
	public LatexMultiply(List<Symbol> paramsSyms, VariableIDTable idList) {
		this();
		for (Symbol next : paramsSyms) params.add(next.latex(idList));
	}
	
	public LatexMultiply(List<Symbol> paramsSyms) {
		this();
	}
	
	protected LinkedList<LatexEvaluatable> popStrengthFromParams(LinkedList<LatexEvaluatable> params, float popedStrength) {
		LinkedList<LatexEvaluatable> buff = new LinkedList<LatexEvaluatable>();
		Iterator<LatexEvaluatable> iterate = params.iterator();
		LatexEvaluatable nextParam;
		while (iterate.hasNext()) {
			nextParam = iterate.next();
			if (nextParam.evaluate().getMultiplyStrength() == popedStrength) {
				buff.add(nextParam);
				iterate.remove();
			}
		}
		return buff;
	}
	
	protected float maxStrength(LinkedList<LatexEvaluatable> params) {
		if (params.size() == 0) return 0;
		float max = params.getFirst().evaluate().getMultiplyStrength();
		float nextStrength;
		for (LatexEvaluatable nextP : params) {
			nextStrength = nextP.evaluate().getMultiplyStrength();
			max = nextStrength > max ? nextStrength : max;
		}
		return max;
	}
	
	protected LinkedList<LatexEvaluatable> unifyStrengths(LinkedList<LinkedList<LatexEvaluatable>> strengths) {
		LinkedList<LatexEvaluatable> buff = new LinkedList<LatexEvaluatable>();
		for (LinkedList<LatexEvaluatable> nextStrength : strengths) {
			for (LatexEvaluatable nextParam : nextStrength) buff.add(nextParam);
		}
		return buff;
	}
	
	///Sorts parameters according to their order of printing
	protected void sortParams() {
		///In strengths list, the BIGGER strengths are stored in the LOWER indices.
		LinkedList<LinkedList<LatexEvaluatable>> strengths = new LinkedList<LinkedList<LatexEvaluatable>>();
		float maxStrength;
		while (this.params.size() > 0) {
			maxStrength = maxStrength(params);
			strengths.add(popStrengthFromParams(this.params, maxStrength));
		}
		
		params = unifyStrengths(strengths);
	}
	
	public void addParam(LatexEvaluatable param) {
		params.add(param);
	}

	@Override
	public String toLatexStr() {
		this.sortParams();
		LatexMultiParameters latParams = new LatexMultiParameters(this.params, this.MULTI, this.strength());
		return latParams.toLatexStr();
	}

	@Override
	public Symbol toSymbol(VariableIDTable table) {
		List<Symbol> paramsSyms = new LinkedList<Symbol>();
		for (LatexEvaluatable next : params) paramsSyms.add(next.evaluate().toSymbol(table));
		return new Multiplication(paramsSyms);
	}

}
