package yekocalc;

import java.util.LinkedList;
import java.util.List;

class PolynomInfo {
	private boolean isAPolynom;
	private List<PolynomVarInfo> vars;
	private boolean zeroDegree; //A zero degree is not related to a specific variable
								//(x^0 = y^0 = 1)
	
	public static PolynomInfo emptyPolynom() {
		PolynomInfo buff = new PolynomInfo();
		buff.makeEmptyPolynom();
		return buff;
	}
	
	public static PolynomInfo notPolynom() {
		return new PolynomInfo(null, false, false);
	}
	
	public void makeNotPolynom() {
		this.init(new LinkedList<PolynomVarInfo>(), false, false);
	}
	
	public void makeEmptyPolynom() {
		this.init(new LinkedList<PolynomVarInfo>(), false, true);
	}
	
	private void init(List<PolynomVarInfo> varsP, boolean zeroDegree, boolean isPolynomP) {
		vars = varsP;
		isAPolynom = isPolynomP;
	}
	
	//Returns -1 on fail
	public int varIndex(Variable v) {
		for (int i = 0; i < vars.size(); i++) {
			if (v.equals(vars.get(i).getVariable())) return i;
		}
		return -1;
	}
	
	private void addVarInfo(PolynomVarInfo inf) {
		vars.add(inf);
	}
	
	private void updateVarInfo(int index, PolynomVarInfo newInf) {
		vars.set(index, newInf);
	}
	
	public PolynomInfo(List<PolynomVarInfo> varsP, boolean zeroDegreeP, boolean isPolynomP) {
		init(varsP, zeroDegreeP, isPolynomP);
	}
	
	public PolynomInfo(List<PolynomVarInfo> varsP, boolean zeroDegreeP) {
		init(varsP, zeroDegreeP, true);

	}
	
	public PolynomInfo() {
		init(new LinkedList<PolynomVarInfo>(), false, true);

	}
	
	public boolean getIsPolynom() {
		return isAPolynom;
	}
	
	public void setZeroDeg(boolean val) {
		zeroDegree = val;
	}
	public boolean getZeroDeg() {
		return zeroDegree;
	}
	
	public void setInfo(PolynomVarInfo inf) {
		Variable varInfo = inf.getVariable();
		int index = varIndex(varInfo);
		if (index == -1) addVarInfo(inf);
		else updateVarInfo(index, inf);
	}
	
	public PolynomVarInfo getInfo(Variable var) throws IllegalArgumentException {
		int index = varIndex(var);
		if (index == -1) {
			return new PolynomVarInfo(var, 0);
		}
		else return vars.get(index);
	}
	
	public PolynomVarInfo getInfo(int index) {
		return vars.get(index);
	}
	
	public int varCount() {
		return vars.size();
	}
}