package yekocalc;

public class IsSymbolAlwaysEven implements SymbolVisitor {
	private boolean isEven;
	
	public IsSymbolAlwaysEven() {
		
	}

	public boolean isAlwaysEven(Symbol sym) {
		sym.accept(this);
		return isEven;
	}
	
	@Override
	public void visit(NumberSym sym) {
		isEven = sym.id().div(new CalcNumber(2)).isInteger();
	}

	@Override
	public void visit(Variable sym) {
		isEven = false;
	}

	@Override
	public void visit(Addition sym) {
		isEven = false;
	}

	@Override
	public void visit(Multiplication sym) {
		isEven = false;
	}

	@Override
	public void visit(Power sym) {
		isEven = false;
	}

	@Override
	public void visit(Fraction sym) {
		isEven = false;
	}

	@Override
	public void visit(Root sym) {
		isEven = false;
	}

	@Override
	public void visit(PlusMinus sym) {
		sym.parameter().accept(this);;
	}

}
