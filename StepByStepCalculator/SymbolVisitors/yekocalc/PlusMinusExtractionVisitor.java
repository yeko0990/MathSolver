package yekocalc;

///Returns true if the symbol is a plusMinus and should be extracted.
///Extraction occurs when a plusminus contains another plusminus as a parameter
///EXAMPLE: +-(+-x) -> +-x
public class PlusMinusExtractionVisitor implements SymbolVisitor {
	private Symbol ret;
	
	public PlusMinusExtractionVisitor() {
		
	}
	
	public Symbol shouldExtractPlusMinus(Symbol sym) {
		sym.accept(this);
		return ret;
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
		ret = null;
	}

	@Override
	public void visit(Multiplication sym) {
		ret = null;
	}

	@Override
	public void visit(Power sym) {
		ret = null;
	}

	@Override
	public void visit(Fraction sym) {
		ret = null;
	}

	@Override
	public void visit(Root sym) {
		ret = null;
	}
	
	@Override
	public void visit(PlusMinus sym) {
		ret = sym;
	}

}
