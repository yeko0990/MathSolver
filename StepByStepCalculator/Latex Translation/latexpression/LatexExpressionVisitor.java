package latexpression;

public interface LatexExpressionVisitor {
	public void visit(LatexAddition add);
	public void visit(LatexMultiply add);
	public void visit(LatexDivision add);
	public void visit(LatexNumber add);
	public void visit(LatexVariable add);
	public void visit(LatexPower add);
	public void visit(LatexRoot add);
	public void visit(LatexPlusMinus add);

}
