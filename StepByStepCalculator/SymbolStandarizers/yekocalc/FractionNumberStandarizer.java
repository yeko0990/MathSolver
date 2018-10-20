package yekocalc;

///If denominator is 1, the standarized symbol is the numerator.
public class FractionNumberStandarizer extends SymbolStandarizer {
	SymbolFilter<NumberInfo> filter;
	public FractionNumberStandarizer(SymbolFilter<NumberInfo> numFilter) {
		filter = numFilter;
	}

	private boolean isDenominatorEqualsOne() {
		return super.sym.getParams().get(1).equals(NumberSym.doubleSymbol(1)); ///TODO replace numberSym argument with
																			   ///a static numberSym of 1.
	}
	
	@Override
	public Symbol internalStandarize(MutableBoolean tookAction) {
		if (isDenominatorEqualsOne()) {
			tookAction.set(true);
			return sym.getParams().get(0);
		}
		else {
			tookAction.set(false);
			return sym;
		}
	}

}
