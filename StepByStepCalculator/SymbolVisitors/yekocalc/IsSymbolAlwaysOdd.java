package yekocalc;

///Checks whether the symbol is ODD. If we have no answer, we return FALSE- It does not mean 
///that the symbol is neccecarily even.
public class IsSymbolAlwaysOdd implements SymbolVisitor {
	private boolean ret;
	
	public IsSymbolAlwaysOdd() {
		
	}
	
	public boolean isAlwaysOdd(Symbol sym) {
		sym.accept(this);
		return ret;
	}

 	@Override
	public void visit(NumberSym sym) {
 		ret = !sym.id().div(new CalcNumber(2)).isInteger();
	}

	@Override
	public void visit(Variable sym) {
		ret = false;
	}

	@Override
	public void visit(Addition sym) {
		ret = false;
	}

	@Override
	public void visit(Multiplication sym) {
		ret = false;
	}

	@Override
	public void visit(Power sym) {
		ret = false;
	}

	@Override
	public void visit(Fraction sym) {
		ret = false;
	}

	@Override
	public void visit(Root sym) {
		ret = false;
	}

	@Override
	public void visit(PlusMinus sym) {
		sym.parameter().accept(this);
	}

}
