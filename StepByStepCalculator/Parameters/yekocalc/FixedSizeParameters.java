package yekocalc;

import java.util.List;

public class FixedSizeParameters extends Parameters {
	FixedSizeList<Symbol> parameters;
	
	public FixedSizeParameters(ParameterEquator equatorP, int paramsCount) {
		super(equatorP);
		parameters = new FixedSizeList<Symbol>(paramsCount);
	}

	@Override
	public List<Symbol> getParameters() {
		return parameters;
	}

	@Override
	public void setParameters(List<Symbol> newParams) {
		if (newParams.size() != parameters.size()) throw new IllegalArgumentException();
		else {
			for (int i = 0; i < newParams.size(); i++) {
				parameters.set(i, newParams.get(i));
			}
		}
	}

}
