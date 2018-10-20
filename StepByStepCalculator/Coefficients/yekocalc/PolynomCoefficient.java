package yekocalc;
import java.util.*;

///A POLYNOM COEFFICIENT is a coefficient that contains no negative exponents. For example,
///the polynom coefficient of x^2 in 2x is 0, not 2x^-1.
public class PolynomCoefficient {
	public static final Symbol onNegativePower = NumberSym.zero; ///Returned as a coefficient if one of the extracted
														  		 ///symbols has a negative exponent in the raw coefficient.
	private Symbol coefficient;
	
	private Symbol getPolyCoefficient(DissolvedSymbol sym, DissolvedSymbol toExtract) {
		DissolvedSymbol rawCoefficient = sym.getCoefficient(toExtract);
		Set<Symbol> extractedSyms = toExtract.dissolvedSymbols();
		for (Symbol nextExtracted : extractedSyms) {
			if (rawCoefficient.hasNegativeExponent(nextExtracted)) return onNegativePower;
		}
		return rawCoefficient.toFraction(); ///TODO Consider changing to powers. But fraction should be fine
	}
	
	public PolynomCoefficient(DissolvedSymbol sym, DissolvedSymbol toExtract) {
			coefficient = getPolyCoefficient(sym, toExtract);
	}
	
	public Symbol getCoefficient() {
		return coefficient;
	}
}
