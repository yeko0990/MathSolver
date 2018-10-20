package yekocalc;

import java.util.List;

public class PowerUnifier extends RepeatingFunctionUnifier {
	private Multiplication mul;
	
	public PowerUnifier(Multiplication mul) {
		this.mul = mul;
	}
	
	@Override
	public boolean isLightSimplification() {
		return true;
	}
	
	@Override
	public List<Symbol> getParameters() {
		return mul.getParams();
	}
	
	@Override
	public void setParameters(List<Symbol> newParams) {
		mul.setParams(newParams);
	}
	
	@Override
	protected Unifiable getEffectiveUnifiable(Symbol sym, int index) {
		Symbol unifiableSymbol = getUnifiableSymbol(sym);
		if (unifiableSymbol == null) return null;
		return new PowerUnifiable(unifiableSymbol, sym, index);
	}
	
	private Symbol getUnifiableSymbol(Symbol sym) {
		if (sym.getType() == SymType.Power) {
			return tryReturn(sym.getParams().get(0)); ///Return base of the power
		}
		else return tryReturn(sym);
	}
	
	///Returns null if toReturn is not an EFFECTIVE unifiable.
	private Symbol tryReturn(Symbol toReturn) { 
		return toReturn.getIdentifier().isTranslatedNumber().getIsNum() ||
				toReturn.isImplicatedNumber() ||
				toReturn.isRawNumber()
				? null : toReturn;
	}

	@Override
	protected UnifiableGroup createGroup(Symbol unifiable, int index) {
		return new PowerUnifiableGroup(unifiable, index);
	}

}
