package latexpression;

import java.util.List;

public class LatexAdditionParameters extends LatexParameters {
	private final String MINUS_STR = "-";
	private final String PLUS_MINUS_STR = "\\pm";
	
	public LatexAdditionParameters(List<LatexEvaluatable> params, String operator, float operatorStrength) {
		super(params, operator, operatorStrength);
	}
	
	@Override
	protected boolean shouldNest(String param, float paramStrength, int paramIndex) {
		return paramStrength <= this.operatorStrength;
	}
	
	private boolean shouldPutOperator(String nextParam, String dontPutIfStartsWith) {
		if (nextParam.length() < dontPutIfStartsWith.length()) return true;
		
		if (nextParam.substring(0, dontPutIfStartsWith.length()).equals(dontPutIfStartsWith)) return false;
		else return true;
	}
	
	///TODO MUCCCHHH better design is to create a visitor for this, so when we add new expression types we wont forget to
	///		update this function in case the new expression type is not expecting an addition operator before itself
	///		when it's a part of an addition.
	///Put operator ONLY if the next param first characters are not minus character OR a plus-minus character.
	///Example: add(4, 5, -6) -> 4+5-6, not 4+5+-6.
	@Override
	protected void buildNextParam(StringBuilder str, LatexEvaluatable param, int paramIndex) {
		String paramStr = super.paramStr(param);
		if (shouldPutOperator(paramStr, MINUS_STR) && shouldPutOperator(paramStr, PLUS_MINUS_STR)) super.putOperator(str);
		//else if () super.putOperator(str);
		
		//else if (! (param.evaluate() instanceof LatexPlusMinus)) super.putOperator(str);
		
		super.putParam(str, param, paramIndex);
	}

}
