package yekocalc;

import java.util.List;

///A translated number identifier
public class ParametersAdditionIdentifier extends AllParametersNumber {
	public ParametersAdditionIdentifier(Symbol sP) {
		super(sP);
	}
	///params size is guaranteed to be at least 1
	@Override
	protected NumberInfo handleParameters(List<NumberInfo> params) {
		NumberInfo buff = params.get(0);
		CalcNumber nextNum, currNum = buff.getNum();
		for (int i = 1; i < params.size(); i++) {
			nextNum = params.get(i).getNum();
			currNum = currNum.add(nextNum);
		}
		buff.setNum(currNum);
		return buff;
	}

}
