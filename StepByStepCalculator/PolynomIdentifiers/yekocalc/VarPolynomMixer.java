package yekocalc;

///Takes a list of polynom var infos and returns a polynomvarinfo in which if and only if degree i exists
///in any of the infos of the list, than it exists in the returned info.
class VarPolynomMixer {
	private PolynomInfo ret;
	
	public VarPolynomMixer() {
		ret = PolynomInfo.emptyPolynom();
	}
	
	private void mixDegrees(PolynomVarInfo mixing) {
		PolynomVarInfo retVar = ret.getInfo(mixing.getVariable());
		for (int i = 1; i <= mixing.getMaxDegree(); i++) {
			if (mixing.getDegreeExists(i)) {
				retVar.setDegreeExists(i, true);
			}
		}
		ret.setInfo(retVar);
	}
	
	public PolynomInfo getInfo() {
		return ret;
	}
	
	public void mixInfo(PolynomVarInfo newVar) {
		mixDegrees(newVar);
	}
	public void mixZeroDeg(boolean zeroDeg) {
		if (zeroDeg) ret.setZeroDeg(true);
	}
	public void mixZeroDeg(PolynomInfo mixedInf) {
		mixZeroDeg(mixedInf.getZeroDeg());
	}
}