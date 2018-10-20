package yekocalc;
import java.util.*;

///Finds all of the variables that are present in the symbol
public class SymbolVarList implements SymbolVisitor {
	HashMap<CalcNumber, Boolean> retIDs;
	
	public SymbolVarList() {
		
	}
	
	public LinkedList<CalcNumber> vars(Symbol sym) {
		retIDs = new HashMap<CalcNumber, Boolean>();
		sym.accept(this);
		return new LinkedList<CalcNumber>(retIDs.keySet());
	}
	
	private void visitParameters(List<Symbol> parameters) {
		for (Symbol nextSym : parameters) nextSym.accept(this);
	}

	@Override
	public void visit(NumberSym sym) {
		return;
	}

	@Override
	public void visit(Variable sym) {
		retIDs.put(sym.id(), true);
	}

	@Override
	public void visit(Addition sym) {
		visitParameters(sym.getParams());
	}

	@Override
	public void visit(Multiplication sym) {
		visitParameters(sym.getParams());
	}

	@Override
	public void visit(Power sym) {
		visitParameters(sym.getParams());
	}

	@Override
	public void visit(Fraction sym) {
		visitParameters(sym.getParams());
	}

	@Override
	public void visit(Root sym) {
		visitParameters(sym.getParams());
	}
	
	@Override
	public void visit(PlusMinus sym) {
		visitParameters(sym.getParams());
	}

}
