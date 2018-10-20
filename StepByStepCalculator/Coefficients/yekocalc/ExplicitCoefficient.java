package yekocalc;

import java.util.LinkedList;
import java.util.Set;

///TODO describe this
public class ExplicitCoefficient {
	private Symbol sym;
	private Symbol base;
	
	public ExplicitCoefficient(Symbol sym, Symbol base) {
		this.sym = sym;
		this.base = base;
	}
	
	private Symbol getDirectDegreeCoefficient(DissolvedSymbol disSym, DissolvedSymbol disBase) {
		//DissolvedSymbol dissolved = baseSym.toDissolvedSymbol();
		//Symbol exponent = dissolved.getDissolvedExponent(base);
		//if (!exponent.equals(deg)) return NumberSym.zero;
		//else {
		//	dissolved.setDissolvedExponent(base, NumberSym.zero);
		//	return dissolved.toPowers();
		//}
		Set<Symbol> bases = disBase.dissolvedSymbols();
		for (Symbol nextBase : bases) {
			if (!disSym.getDissolvedExponent(nextBase).equals(disBase.getDissolvedExponent(nextBase))) return NumberSym.zero;
			else disSym.setDissolvedExponent(nextBase, NumberSym.zero);
		}
		return disSym.toFraction();
	}
	
	public Symbol getExplicitCoefficient() {
		DissolvedSymbol disSym = sym.toDissolvedSymbol();
		DissolvedSymbol disBase = base.toDissolvedSymbol();
		
		if (sym.getType() == SymType.Addition) {
			LinkedList<Symbol> coefs = new LinkedList<Symbol>();
			for (Symbol next : sym.getParams()) {
				Symbol nextCoef = this.getDirectDegreeCoefficient(next.toDissolvedSymbol(), disBase);
				if (!nextCoef.equals(NumberSym.zero)) coefs.add(nextCoef);
			}
			if (coefs.size() == 0) return NumberSym.zero;
			return new Addition(coefs).standarizedForm();
		}
		
		else return this.getDirectDegreeCoefficient(disSym, disBase);
	}

}
