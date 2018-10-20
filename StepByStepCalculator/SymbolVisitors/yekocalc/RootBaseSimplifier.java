package yekocalc;

///A simplification of a root which is dependant of the type of its base.
//Example: sqrt(x^2) -> +-x
public class RootBaseSimplifier implements SymbolVisitor, DirectSimplifier {
	private boolean didAction;
	private Root root;
	
	public RootBaseSimplifier(Root root) {
		this.root = root;
	}
	
	@Override
	public boolean isLightSimplification() {
		return false;
	}
	
	@Override
	public boolean simplifyNext() {
		root.base().accept(this);
		return didAction;
	}
	
	private void noAction() {
		didAction = false;
	}

	@Override
	public void visit(NumberSym sym) {
		noAction();
	}

	@Override
	public void visit(Variable sym) {
		noAction();
	}

	@Override
	public void visit(Addition sym) {
		noAction();
	}

	@Override
	public void visit(Multiplication sym) {
		noAction();
	}

	@Override
	public void visit(Power sym) {
		///TODO IMPORTANT- Implement transformation of root of a power to a PLUS-MINUS if the exponent is zugi.
		if (sym.getExponent().equals(this.root.rootBy())) {
			didAction = true;
			root.setRootBy(NumberSym.intSymbol(1));
			root.setBase(sym.getBase());
		}
		else didAction = false;
	}

	@Override
	public void visit(Fraction sym) {
		noAction();
	}

	@Override
	public void visit(Root sym) {
		noAction(); ///TODO simplify root inside a root
	}
	
	@Override
	public void visit(PlusMinus sym) {
		noAction();
	}

}
