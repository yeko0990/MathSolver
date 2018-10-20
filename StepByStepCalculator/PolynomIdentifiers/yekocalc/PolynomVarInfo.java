package yekocalc;

import java.util.LinkedList;
import java.util.List;

class PolynomVarInfo {
	private Variable v;
	private int maxDegree; ///max degree of the variable
	PolynomDegrees degrees; ///stores whether a certain degree exists in this polynom
									
	//private void initDegs(boolean[] degsP) {
	//	degrees = PolynomDegrees.parse(degsP);
	//}
	
	private void initDegs(PolynomDegrees degsP) {
		degrees = degsP;
	}
	
	private void init(int deg, Variable vP, PolynomDegrees degsP) {
		maxDegree = deg;
		v = vP;
		initDegs(degsP);
	}
	
	public PolynomVarInfo(Variable v, int deg) {
		init(deg, v, new PolynomDegrees());
	}
	public PolynomVarInfo(Variable v) {
		init(0, v, new PolynomDegrees());
	}
	public PolynomVarInfo() {
		init(0, null, new PolynomDegrees());
	}
	public int getMaxDegree() {
		return maxDegree;
	}
	
	///was once public, now private to prevent errors.
	private void setMaxDegree(int degP) {
		maxDegree = degP;
	}
	
	public void updateMaxDegree(int prevMax) {
		int i = prevMax;
		while (!degrees.getDegree(i)) i--;
		setMaxDegree(i);
	}
	
	public boolean getDegreeExists(int deg) {
		return degrees.getDegree(deg);
	}
	public void setDegreeExists(int deg, boolean degExists) {
		if (degExists && deg > maxDegree) setMaxDegree(deg);
		degrees.setDegree(deg, degExists);
		///if (!degExists && deg == maxDegree) updateMaxDegree(deg);
	}
	
	public Variable getVariable() {
		return v;
	}
	
	public PolynomDegrees getDegsCopy() {
		return degrees.copy(maxDegree);
	}
	
	public boolean[] getAllDegrees() {
		boolean[] buff = new boolean[maxDegree+1];
		for (int i = 0; i < maxDegree+1; i++) {
			buff[i] = getDegreeExists(i);
		}
		return buff;
	}
	
	///Return all degrees that mathces a certain value (exists, dont exists)
	public List<Integer> matchingDegs(boolean exists) {
		LinkedList<Integer> matches = new LinkedList<Integer>();
		for (int i = 1; i <= maxDegree; i++) {
			if (getDegreeExists(i) == exists) matches.add(i);
		}
		return matches;
	}
}