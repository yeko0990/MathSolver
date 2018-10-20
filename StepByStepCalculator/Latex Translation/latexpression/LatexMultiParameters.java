package latexpression;

import java.util.List;

public class LatexMultiParameters extends LatexParameters {
	private final String MINUS = "-";
	private final String MINUS_ONE = "-1";
	
	public LatexMultiParameters(List<LatexEvaluatable> params, String operator, float operatorStrength) {
		super(params, operator, operatorStrength);
	}
	
	protected void buildNextParam(StringBuilder str, LatexEvaluatable param, boolean previousParamIsMinus, int paramIndex) {
		//if (param.evaluate().getMultiplyStrength() >= preParam.evaluate().getMultiplyStrength()) super.putOperator(str);
		InjectMultiplyOperatorVisitor visitor = new InjectMultiplyOperatorVisitor();
		if (visitor.shouldInjectBeforeExpression(param.evaluate(), previousParamIsMinus )) super.putOperator(str);
		super.putParam(str, param, paramIndex);
	}
	
	///If firstParam is -1, than puts only MINUS_CHAR (example: dont print (-1)*x, but print -x)
	///RETURNS TRUE IF FIRST PARAM WAS -1!
	protected boolean putFirstParam(StringBuilder str, LatexEvaluatable firstParam) {
		String first = firstParam.evaluate().toLatexStr();
		if (first.equals(MINUS_ONE)) { ///TODO Check if a number equals minus one in a more formal way...
			str.append(MINUS); 
			return true;
		}
		else {
			super.putParam(str, firstParam, 0);
			return false;
		}
	}
	
	@Override
	public String toLatexStr() {
		if (params.size() < 1) return "";
		StringBuilder str = new StringBuilder();
		boolean preParamIsMinus;
		preParamIsMinus = putFirstParam(str, params.get(0));
		for (int i = 1; i < params.size(); i++) {
			buildNextParam(str, params.get(i), preParamIsMinus, i); 
			preParamIsMinus = false;
		}
		return str.toString();
	}

}
