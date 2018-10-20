package yekocalc;

public interface SymbolVisitor {
	public void visit(NumberSym sym);
	public void visit(Variable sym);
	public void visit(Addition sym);
	public void visit(Multiplication sym);
	public void visit(Power sym);
	public void visit(Fraction sym);
	public void visit(Root sym);
	public void visit(PlusMinus sym);
}
