package yekocalc;

class NumberInfo {
	private boolean isNum;
	private CalcNumber num;
	
	public static NumberInfo notNumber() {
		return new NumberInfo(null, false);
	}
	
	public NumberInfo(CalcNumber cn, boolean isNumP) {
		num = cn;
		isNum = isNumP;
	}
	public NumberInfo(CalcNumber cn) {
		num = cn;
		isNum = true;
	}
	public NumberInfo() {
		isNum = false;
		num = null;
	}
	public CalcNumber getNum() {
		return num;
	}
	
	public void setNum(CalcNumber numP) {
		num = numP;
	}
	
	public boolean getIsNum() {
		return isNum;
	}
	public void setIsNum(boolean isP) {
		isNum = isP;
	}
}