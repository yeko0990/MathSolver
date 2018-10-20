package yekocalc;

public class FractionPolynomIdentifier extends PolynomIdentifier {

	public FractionPolynomIdentifier(Symbol sym) {
		super(sym);
	}
	
	private PolynomInfo polynomInfo(PolynomInfo numerator, NumberInfo denominator) {
		if (!numerator.getIsPolynom() || !denominator.getIsNum()) return PolynomInfo.notPolynom();
		return numerator;
	}

	@Override
	public PolynomInfo isPolynom() {
		Symbol numerator = s.getParams().get(0);
		Symbol denominator = s.getParams().get(1);
		return polynomInfo(numerator.getIdentifier().isPolynom(), denominator.getIdentifier().isTranslatedNumber());
	}

}
