package yekocalc;

import java.util.List;

class MultiPolynomIdentifier extends PolynomIdentifier {
	public MultiPolynomIdentifier(Symbol sP) {
		super (sP);
	}
	
	@Override
	public PolynomInfo isPolynom() {
		List<PolynomInfo> childInfos = getChildrenPolynomInfos();
		PolynomInfoListMultiply adder = new PolynomInfoListMultiply(childInfos);
		
		if (!isEveryChildPolynom(childInfos)) registerNotPolynom();
		else inf = adder.add();
		return inf;
	}
}