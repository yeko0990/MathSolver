package yekocalc;

public class PowerNumberStandarizer extends SymbolStandarizer {
	private SymbolFilter<NumberInfo> numberFilter;
	private final CalcNumber ZERO = CalcNumber.ZERO;
	private final CalcNumber ONE = new CalcNumber(1);
	
	public PowerNumberStandarizer(SymbolFilter<NumberInfo> filter) {
		super();
		numberFilter = filter;
	}
	
	private Symbol getBase() {
		return sym.getParams().get(0);
	}
	
	private Symbol getExpo() {
		return sym.getParams().get(1);
	}
	
	private boolean isExponentEqualsNumber(CalcNumber toNum) {
		NumberInfo expoInf = numberFilter.filter(getExpo());
		return expoInf.getIsNum() && toNum.equals(expoInf.getNum()); ///TODO What if expoInf.getNum() is null?
																   ///Check CalcNumber::equals can handle null parameter.
	}
	
	@Override
	public Symbol internalStandarize(MutableBoolean tookAction) {
		if (isExponentEqualsNumber(ONE)) {
			tookAction.set(true);
			return getBase();
		}
		
		///TODO if base equals zero, THAN POWER OF ZERO BY ZERO IS UNDEFINED! Handle that.
		else if (isExponentEqualsNumber(ZERO)) {
			tookAction.set(true);
			return new NumberSym(ONE);
		}
		else {
			tookAction.set(false);
			return sym;
		}
	}

}
