package yekocalc;

import java.util.List;

public class ParametersPowerIdentifier extends AllParametersNumber {
	public ParametersPowerIdentifier(Power sP) {
		super(sP);
	}

	@Override
	protected NumberInfo handleParameters(List<NumberInfo> params) {
		NumberInfo finalInfo = params.get(0);
		finalInfo.setNum(finalInfo.getNum().pow(params.get(1).getNum()));
		return finalInfo;
	}

}
