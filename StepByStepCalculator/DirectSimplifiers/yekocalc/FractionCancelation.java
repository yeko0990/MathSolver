package yekocalc;

public class FractionCancelation implements DirectSimplifier {
	private Fraction frac;
	
	public FractionCancelation(Fraction frac) {
		this.frac = frac;
	}

	@Override
	public boolean isLightSimplification() {
		return false;
	}
	
	@Override
	public boolean simplifyNext() {
		///TODO OPTIMIZATION- first check if frac needs simplification: check if the numerator contains a base
		///					  that exists in the denominator and if yes than simplify.
		///					  This mainly removes the need to call equals.
		Fraction newFrac = frac.toDissolvedSymbol().toUnstandarizedFraction();
		if (newFrac.equals(frac)) return false;
		
		else {
			frac.setNumerator(newFrac.numerator());
			frac.setDenominator(newFrac.denominator());
			frac.standarizedForm(); ///Because newFrac is unstandarized
			return true;
		}
	}

}
