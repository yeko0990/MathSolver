package yekocalc;

public class FractionZeroNumerator implements DirectSimplifier {
	private Fraction frac;
	
	public FractionZeroNumerator(Fraction frac) {
		this.frac = frac;
	}

	@Override
	public boolean isLightSimplification() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean simplifyNext() {
		if (frac.numerator().equals(NumberSym.zero)) {
			frac.setDenominator(NumberSym.intSymbol(1));
			return true;
		}
		else return false;
	}

}
