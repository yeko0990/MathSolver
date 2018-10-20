package yekocalc;

import java.util.List;

public class AlwaysEqualParameterEquator extends ParameterEquator {
	@Override
	protected boolean internalEquals(List<Symbol> params1, List<Symbol> params2) {
		return true;
	}
	
	@Override
	public int parametersHashCode(List<Symbol> params) {
		return 0;
	}

}
