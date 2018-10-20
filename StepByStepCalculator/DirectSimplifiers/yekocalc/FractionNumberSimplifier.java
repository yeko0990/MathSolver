package yekocalc;

public class FractionNumberSimplifier implements DirectSimplifier {
	private Fraction frac;
	SymbolFilter<NumberInfo> filter;
	
	public FractionNumberSimplifier(Fraction frac, SymbolFilter<NumberInfo> numFilter) {
		this.frac = frac;
		filter = numFilter;
	}
	
	@Override
	public boolean isLightSimplification() {
		return false;
	}
 	
	private void calculateDivision(NumberInfo numerator, NumberInfo denominator) {
		CalcNumber result = numerator.getNum().div(denominator.getNum());
		frac.setNumerator(new NumberSym(result));
		frac.setDenominator(NumberSym.doubleSymbol(1)); ///TODO Change denominator to a STATIC
														///		numberSymbol with value of 1.
	}
	
	@Override
	public boolean simplifyNext() {
		NumberInfo numerator = filter.filter(frac.numerator());
		NumberInfo denominator = filter.filter(frac.denominator());
		if (!numerator.getIsNum() || !denominator.getIsNum()) return false;
		
		else {
			calculateDivision(numerator, denominator);
			return true;
		}
	}

}
