package yekocalc;
import java.util.*;

///Adds two polynomVarInfos' degree, REGARDLESS OF THE ZERO DEGREE
class PolynomVarInfoAdder {
	private PolynomVarInfo a,b;
	PolynomVarInfo buff;
	public PolynomVarInfoAdder(PolynomVarInfo aP, PolynomVarInfo bP) {
		a = aP;
		b = bP;
		initBuff();
	}
	
	private void initBuff() {
		Variable var = a.getVariable();
		if (!var.equals(b.getVariable())) throw new IllegalArgumentException();
		else {
			buff = new PolynomVarInfo(var);
		}
	}
	
	private void addADeg(int aDeg, List<Integer> bDegs) {
		for (int i = 0; i < bDegs.size(); i++) {
			buff.setDegreeExists(aDeg + bDegs.get(i), true);
		}
	}
	
	public PolynomVarInfo add() {
		List<Integer> bDegs = b.matchingDegs(true);
		for (int i = 1; i <= a.getMaxDegree(); i++) {
			if (a.getDegreeExists(i)) addADeg(i, bDegs);
		}
		return buff;
	}
}

///Adds degrees of TWO polynoms (for multiplication of polynoms).
class PolynomInfoAdder {
	PolynomInfo a,b;
	PolynomInfo buff;
	public void setAdded(PolynomInfo aP, PolynomInfo bP) {
		a = aP;
		b = bP;
		buff = PolynomInfo.emptyPolynom();
	}
	
	PolynomInfoAdder(PolynomInfo aP, PolynomInfo bP) {
		setAdded(aP, bP);
	}
	
	private PolynomInfo varInfoToPolyInfo(PolynomVarInfo varInf, boolean zeroDeg) {
		LinkedList<PolynomVarInfo> lstBuff = new LinkedList<PolynomVarInfo>();
		lstBuff.add(varInf);
		return new PolynomInfo(lstBuff, zeroDeg, true);
	}
	
	private void includePolynomInfo(PolynomInfo included) {
		PolynomMixer mixer = new PolynomMixer(buff, included);
		buff = mixer.mixPolynoms();
	}
	private void includePolynomVarInfo(PolynomVarInfo included, boolean zeroDeg) {
		includePolynomInfo(varInfoToPolyInfo(included, zeroDeg));
	}

	
	private void addZeroDegs() {
		if (a.getZeroDeg()) includePolynomInfo(b);
		if (b.getZeroDeg()) includePolynomInfo(a);
	}
	
	///a
	private void addVariablesValues(PolynomVarInfo aVarInf, PolynomVarInfo bVarInf) {
		Variable v = aVarInf.getVariable();
		if (!v.equals(bVarInf.getVariable())) throw new IllegalArgumentException();
		else {
			PolynomVarInfoAdder adder = new PolynomVarInfoAdder(aVarInf, bVarInf);
			///buff.setInfo(adder.add()); ///TODO bad line- overwrites buff's existing variable info.
			includePolynomVarInfo(adder.add(), false); //We are including this info with non-existing zero degree,
													   //so buff's zero degree is not changed.
		}
	}
	
	///Adds a's var info with b's var info OR copies their degrees (if the variable is not
	///																the same in both infos).
	private void addVariables(PolynomVarInfo aVarInf, PolynomVarInfo bVarInf) {
		Variable aV, bV;
		aV = aVarInf.getVariable();
		bV = bVarInf.getVariable();
		if (aV.equals(bV)) addVariablesValues(aVarInf,bVarInf);
		else {
			includePolynomVarInfo(aVarInf, a.getZeroDeg());
			includePolynomVarInfo(bVarInf, b.getZeroDeg());
		}
	}
	public PolynomInfo add() {
		addZeroDegs();
		for (int i = 0; i < a.varCount(); i++) {
			for (int j = 0; j < b.varCount(); j++) {
				addVariables(a.getInfo(i), b.getInfo(j));
			}
		}
		return buff;
	}
	//public PolynomInfo getResult() {
	//	
	//}
}

class PolynomInfoListMultiply {
	List<PolynomInfo> infos;
	PolynomInfo ret;
	public PolynomInfoListMultiply(List<PolynomInfo> infosP) {
		infos = infosP;
		ret = PolynomInfo.emptyPolynom();
		ret.setZeroDeg(true);
	}
	
	public PolynomInfo add() {
		if (infos.size() == 0) {
			throw new IllegalArgumentException();
		}
		PolynomInfoAdder adder = new PolynomInfoAdder(ret, infos.get(0));
		ret = adder.add();
		for (int i = 1; i < infos.size(); i++) {
			adder.setAdded(ret, infos.get(i));
			ret = adder.add();
		}
		return ret;
	}
}