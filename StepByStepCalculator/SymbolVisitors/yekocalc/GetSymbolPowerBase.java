package yekocalc;

public class GetSymbolPowerBase implements SymbolVisitor {
	private Symbol base;
	
	public GetSymbolPowerBase() {
		
	}

	public Symbol getBase(Symbol sym) {
		sym.accept(this);
		return base;
	}
	
	@Override
	public void visit(NumberSym sym) {
		base = sym;
	}

	@Override
	public void visit(Variable sym) {
		base = sym;
	}

	@Override
	public void visit(Addition sym) {
		base = sym;
	}

	@Override
	public void visit(Multiplication sym) {
		base = sym;
	}

	@Override
	public void visit(Power sym) {
		base = sym.getBase();
	}

	@Override
	public void visit(Fraction sym) {
		base = sym;
	}

	@Override
	public void visit(Root sym) {
		base = sym;
	}

	@Override
	public void visit(PlusMinus sym) {
		base = sym;
	}

}
