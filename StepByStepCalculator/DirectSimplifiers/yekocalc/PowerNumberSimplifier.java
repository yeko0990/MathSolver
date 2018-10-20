package yekocalc;

public class PowerNumberSimplifier implements DirectSimplifier {
	private Power pow;
	SymbolFilter<NumberInfo> numberFilter;
	
	@Override
	public boolean isLightSimplification() {
		return false;
	}
	
	public PowerNumberSimplifier(Power pow, SymbolFilter<NumberInfo> numFilter) {
		this.pow = pow;
		numberFilter = numFilter;
	}
	
	private boolean bothAreNumbers(NumberInfo n1, NumberInfo n2) {
		return n1.getIsNum() && n2.getIsNum();
	}
	
	private void calculatePower(CalcNumber baseNum, CalcNumber expoNum) {
		pow.setBase(new NumberSym(baseNum.pow(expoNum)));
		pow.setExponent(new NumberSym(new CalcNumber(1)));
	}
	
	@Override
	public boolean simplifyNext() {
		Symbol baseSym = pow.getBase();
		Symbol expoSym = pow.getExponent();
		NumberInfo baseNum = numberFilter.filter(baseSym);
		NumberInfo expoNum = numberFilter.filter(expoSym);
		if (!bothAreNumbers(baseNum, expoNum)) return false;
		else {
			calculatePower(baseNum.getNum(), expoNum.getNum());
			return true;
		}
	}

}
