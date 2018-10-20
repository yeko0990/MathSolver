package yekocalc;
import java.util.*;

public class ParameterOrderedEquator extends ParameterEquator {
	@Override
	protected boolean internalEquals(List<Symbol> params1, List<Symbol> params2) {
		if (params1.size() != params2.size()) return false;
		Symbol next1, next2;
		for (int i = 0; i < params1.size(); i++) {
			next1 = params1.get(i);
			next2 = params2.get(i);
			if (!next1.equals(next2)) return false;
		}
		return true;
	}
	
	@Override
	public int parametersHashCode(List<Symbol> params) {
		return ListUtils.orderedHashCode(params);
	}
}
