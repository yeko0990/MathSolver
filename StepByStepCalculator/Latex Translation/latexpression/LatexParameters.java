package latexpression;
import java.util.*;

public class LatexParameters {
	private final char MINUS = '-';
	float operatorStrength;
	String operator;
	List<LatexEvaluatable> params;
	public LatexParameters(List<LatexEvaluatable> params, String operator, float operatorStrength) {
		this.operatorStrength = operatorStrength;
		this.operator = operator;
		this.params = params;
	}
	
	protected String paramStr(LatexEvaluatable param) {
		return param.evaluate().toLatexStr();
	}
	
	///Should we nest the next variable (nesting == putting parenthessis)
	protected boolean shouldNest(String param, float paramStrength, int paramIndex) {
		if (param.length() >= 1) {
			if (param.charAt(0) == MINUS && paramIndex > 0) return true;
		}
		
		return paramStrength <= this.operatorStrength;
	}
	
	protected void putParam(StringBuilder str, String param, float strength, int paramIndex) {
		boolean nesting = shouldNest(param, strength, paramIndex);
		if (nesting) str.append("(");
		str.append(param);
		if (nesting) str.append(")");
	}
	
	protected void putParam(StringBuilder str, LatexEvaluatable param, int paramIndex) {
		this.putParam(str, param.evaluate().toLatexStr(), param.evaluate().strength(), paramIndex);
	}
	
	protected void buildNextParam(StringBuilder str, LatexEvaluatable param, int paramIndex) {
		putOperator(str);
		putParam(str, param, paramIndex);
	}
	
	protected void putOperator(StringBuilder str) {
		str.append(this.operator);
	}

	
	public String toLatexStr() {
		if (params.size() < 1) return "";
		StringBuilder str = new StringBuilder();
		putParam(str, params.get(0), 0);
		for (int i = 1; i < params.size(); i++) {
			buildNextParam(str, params.get(i), i);
		}
		return str.toString();
	}

}
