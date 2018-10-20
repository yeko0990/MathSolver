package yekocalc;

///A dissolved symbol divided by another dissolvedSymbol.
public class DissolvedFraction {
	DissolvedSymbol numerator, denominator;
	public DissolvedFraction(DissolvedSymbol numerator, DissolvedSymbol denominator) {
		this.numerator = numerator;
		this.denominator = denominator;
		this.divide();
	}
	
	private void divide() {
		
	}
	
	public DissolvedSymbol numerator() {
		return numerator;
	}
	
	public DissolvedSymbol denominator() {
		return denominator;
	}

}
