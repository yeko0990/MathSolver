package yekocalc;

///return true if symbol is interpreted as negative (-x LOOKES negative, even if x is a negative value)
///if you cannot decide, return that is is NOT looks negative.
///BECAUSE A SYMBOL THAT LOOKS NEGATIVE ALWAYS MAY BE MULTIPLIED BY -1 TO LOOK POSITIVE.
public class SymbolLooksNegative implements SymbolVisitor {
	private boolean result;
	
	public SymbolLooksNegative() {
		
	}
	
	public boolean looksNegative(Symbol sym) {
		sym.accept(this);
		return result;
	}

	@Override
	public void visit(NumberSym sym) {
		result = sym.id().isNegative();
	}

	@Override
	public void visit(Variable sym) {
		result = false;
	}

	@Override
	public void visit(Addition sym) {
		result = false; ///TODO maybe not?
	}

	@Override
	public void visit(Multiplication sym) {
		result = sym.getParams().contains(NumberSym.intSymbol(-1));
	}

	@Override
	public void visit(Power sym) {
		result = false;
	}

	@Override
	public void visit(Fraction sym) {
		result = new SymbolLooksNegative().looksNegative(sym.numerator());
	}

	@Override
	public void visit(Root sym) {
		result = false;
	}
	
	@Override
	public void visit(PlusMinus sym) {
		result = false;
	}
}
