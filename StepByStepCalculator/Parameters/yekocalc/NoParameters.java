package yekocalc;

import java.util.List;

public class NoParameters extends Parameters {

	public NoParameters() {
		super(new AlwaysEqualParameterEquator());
	}

	@Override
	public List<Symbol> getParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setParameters(List<Symbol> newParams) {

	}

}
