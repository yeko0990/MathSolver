package yekocalc;

public class IsPlusMinusPresent implements SymbolVisitor {
	private PlusMinus ret;
	
	public IsPlusMinusPresent() {
		
	}
	
	public PlusMinus containsPlusMinus(Symbol sym) {
		sym.accept(this);
		return ret;
	}
	
	//public boolean containsPlusMinus(Symbol sym) {
	//	return this.containsPlusMinus(sym) != null;
	//}

	private void visitParameters(Symbol sym) {
		for (Symbol nextParam : sym.getParams()) {
			nextParam.accept(this);
			if (ret != null) {
				return;
			}
		}
		ret = null;
	}
	
	@Override
	public void visit(NumberSym sym) {
		ret = null;
	}

	@Override
	public void visit(Variable sym) {
		ret = null;

	}

	@Override
	public void visit(Addition sym) {
		visitParameters(sym);
	}

	@Override
	public void visit(Multiplication sym) {
		visitParameters(sym);
	}

	@Override
	public void visit(Power sym) {
		visitParameters(sym);
	}

	@Override
	public void visit(Fraction sym) {
		visitParameters(sym);
	}

	@Override
	public void visit(Root sym) {
		visitParameters(sym);
	}

	@Override
	public void visit(PlusMinus sym) {
		ret = sym;
	}

}
