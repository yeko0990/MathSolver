package yekocalc;

class PowPolynomIdentifier extends PolynomIdentifier {
	private Symbol parameter(int index) {
		return s.getParams().get(index);
	}
	
	private Symbol getBase() {
		return parameter(0);
	}
	
	private Symbol getExponent() {
		return parameter(1);
	}
	
	public PowPolynomIdentifier(Symbol sP) {
		super (sP);
	}
	
	private boolean validPolynom(PolynomInfo baseInfo, NumberInfo expoInfo) {
		if (!baseInfo.getIsPolynom() || !expoInfo.getIsNum()) return false;
		else return true;
	}
	
	/*(private void mixAllUpperDegrees(PolynomVarInfo out, PolynomVarInfo baseInf, int multiplier, int degree) {
		for (int i = degree; i <= baseInf.getMaxDegree(); i++) {
			if (baseInf.getDegreeExists(i)) out.setDegreeExists( degree, degExists);
		}
	}*/
	
	private PolynomInfo getOnePolynomInfo() {
		PolynomInfo buff = new PolynomInfo();
		buff.setZeroDeg(true);
		return buff;
	}
	
	private PolynomInfo multiplyInfo(PolynomInfo baseVar, int exponent) {
		PolynomInfo total = getOnePolynomInfo();
		PolynomInfoAdder adder = new PolynomInfoAdder(total, baseVar);
		for (int i = 0; i < exponent; i++) {
			adder.setAdded(total, baseVar);
			total = adder.add();
		}
		return total;
	}
	
	/*private void updateVarInfo(PolynomInfo out, PolynomVarInfo baseVar, int multiplier) {
		PolynomVarInfo varInf = new PolynomVarInfo(baseVar.getVariable(), baseVar.getMaxDegree() * multiplier);
		for (int i = 0; i <= baseVar.getMaxDegree(); i++) {
			if (baseVar.getDegreeExists(i)) {
				multiplyAllInfos(varInf, baseVar, multiplier, i);
			}
		}
		out.setInfo(varInf);
	}*/
	
	/*private void updatePolynomInfo(PolynomInfo out, PolynomInfo baseInf, NumberInfo expoInf) {
		int count = baseInf.varCount();
		int multi = expoInf.getNum().getInt();
		for (int i = 0; i < count; i++) {
			updateVarInfo(out, baseInf.getInfo(i), multi);
		}
	}*/
	
	///TODO Fix it, not good... specifically updatePolynomInfo
	@Override
	public PolynomInfo isPolynom() {
		PolynomInfo baseInf = new PolynomInfo();
		NumberInfo expoInf = new NumberInfo();
		Symbol base, expo;
		base = getBase();
		expo = getExponent();
		baseInf = base.getIdentifier().isPolynom();
		expoInf = expo.getIdentifier().isRawNumber();
		
		if (!validPolynom(baseInf, expoInf)) registerNotPolynom();
		else {
			inf = multiplyInfo(baseInf, (int) expoInf.getNum().getDouble()); ///TODO What if exponent is not an integer?
		}
		return inf;
	}
}