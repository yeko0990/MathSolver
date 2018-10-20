package yekocalc;

public class FractionImplicatedNumber extends ImplicatedNumberIdentifier {
	private Fraction frac;
	
	public FractionImplicatedNumber(Fraction frac) {
		this.frac = frac;
	}

	@Override
	public boolean isImplicatedNumber() {
		return frac.numerator().isImplicatedNumber() && frac.denominator().isImplicatedNumber();
	}

}
