package yekocalc;

import java.util.HashMap;

class PolynomDegrees {
	private HashMap<Integer, Boolean> degrees;
	private static final Boolean defaultVal = false;
	
	public static PolynomDegrees parse(boolean[] arr) {
		PolynomDegrees newDegs = new PolynomDegrees();
		for (int i = 0; i < arr.length; i++) {
			newDegs.setDegree(i, arr[i]);
		}
		return newDegs;
	}
	
	public PolynomDegrees() {
		degrees = new HashMap<Integer,Boolean>();
	}
	public boolean getDegree(int deg) {
		return degrees.containsKey(deg) ? degrees.get(deg) : defaultVal;
	}
	public void setDegree(int deg, boolean val) {
		degrees.put(deg, val);
	}
	
	public PolynomDegrees copy(int maxDeg) {
		PolynomDegrees newDegs = new PolynomDegrees();
		for (int i = 0; i <= maxDeg; i++) {
			newDegs.setDegree(i, this.getDegree(i));
		}
		return newDegs;
	}
	
}