package yekocalc;

public class PlusMinusZeroExtractor extends SymbolStandarizer {
	PlusMinus pm;
	
	public PlusMinusZeroExtractor(PlusMinus pm) {
		this.pm = pm;
	}

	@Override
	public Symbol internalStandarize(MutableBoolean tookAction) {
		NumberInfo param = pm.parameter().getIdentifier().isRawNumber();
		if (param.getIsNum() && param.getNum().equals(CalcNumber.ZERO)) {
			tookAction.set(true);
			return pm.parameter();
		}
		else {
			tookAction.set(false);
			return pm;
		}
				
	}

}
