package yekocalc;

///Exponent is one if it is not a power (quite trivial)
public class GetSymbolExponent implements SymbolVisitor {
	private Symbol exponent;
	
	public GetSymbolExponent() {
		
	}
	
	public Symbol getExponent(Symbol sym) {
		sym.accept(this);
		return exponent;
	}

	@Override
	public void visit(NumberSym sym) {
		exponent = NumberSym.intSymbol(1); 
	}

	@Override
	public void visit(Variable sym) {
		exponent = NumberSym.intSymbol(1); 
	}

	@Override
	public void visit(Addition sym) {
		exponent = NumberSym.intSymbol(1); 
	}

	@Override
	public void visit(Multiplication sym) {
		exponent = NumberSym.intSymbol(1); 
	}

	@Override
	public void visit(Power sym) {
		exponent = sym.getExponent(); 
	}

	@Override
	public void visit(Fraction sym) {
		exponent = NumberSym.intSymbol(1); 
	}

	@Override
	public void visit(Root sym) {
		exponent = NumberSym.intSymbol(1); 
	}

	@Override
	public void visit(PlusMinus sym) {
		exponent = NumberSym.intSymbol(1); 
	}

}
