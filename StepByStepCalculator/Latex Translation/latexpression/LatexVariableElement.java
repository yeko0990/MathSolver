package latexpression;

public class LatexVariableElement extends LatexElement {
	String varStr;
	
	public LatexVariableElement(char c) {
		super(new ExpectingOperatorPutMultiplies());
		this.varStr = new Character(c).toString();
	}

	@Override
	public boolean isVariable() {
		return true;
	}
	
	@Override
	public void getVariable(VarElementContext context) {
		context.name = this.varStr;
	}

}
