package yekocalc;

public class PowerUnifiable extends Unifiable {

	public PowerUnifiable(Symbol unifiableP, Symbol unifiableFunctionP, int indexP) {
		super(unifiableP, unifiableFunctionP, indexP);
		// TODO Auto-generated constructor stub
	}

	private Argument getPowerArgument() {
		return new Argument(unifiableFunction.getParams().get(1), unifiableFunction);
	}
	
	private Argument getOtherArgument() {
		return new Argument(NumberSym.doubleSymbol(1), unifiableFunction);
	}
	
	
	@Override
	public Argument getRepetitionArgument() {
		if (this.unifiableFunction.getType() == SymType.Power) return getPowerArgument();
		else return getOtherArgument();
	}

}
