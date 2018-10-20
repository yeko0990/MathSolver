package yekocalc;

import java.util.List;

class AdditionPolynomIdentifier extends PolynomIdentifier {
	public AdditionPolynomIdentifier(Symbol sP) {
		super(sP);
	}
	
	@Override
	public PolynomInfo isPolynom() {
		List<PolynomInfo> childInfos = getChildrenPolynomInfos();
		PolynomMixer listExtendor = new PolynomMixer(childInfos);
		
		if(!isEveryChildPolynom(childInfos)) registerNotPolynom();
		else inf = listExtendor.mixPolynoms();
		
 		return inf;
	}
 
}