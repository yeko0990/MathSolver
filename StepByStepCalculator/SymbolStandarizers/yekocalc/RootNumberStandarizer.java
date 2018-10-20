package yekocalc;

public class RootNumberStandarizer extends SymbolStandarizer {
	
	public RootNumberStandarizer() {
		super();
	}

	private Symbol base(Symbol root) {
		return root.getParams().get(0);
	}
	
	private Symbol rootBy(Symbol root) {
		return root.getParams().get(1);
	}
	
	@Override
	public Symbol internalStandarize(MutableBoolean tookAction) {
		if (rootBy(this.sym).equals(NumberSym.intSymbol(1))) {
			tookAction.set(true);
			return base(this.sym);
		}
		else {
			tookAction.set(false);
			return this.sym;
		}
	}

}
