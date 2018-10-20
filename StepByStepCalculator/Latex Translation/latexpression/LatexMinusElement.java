package latexpression;

public class LatexMinusElement extends LatexElement {
	
	public LatexMinusElement() {
		super(new NeverPutMultiplies());
	}
	
	@Override
	boolean isOperator(Operator op) {
		return op == Operator.Minus;
	}
	
	///Used to spawn a '(-1)*' sign
	private void addMinus(ArithmaticOpContext context) {
		context.addToCurrentParameter(LatexStaticElements.OPEN);
		context.addToCurrentParameter(LatexStaticElements.MINUS);
		context.addToCurrentParameter(new LatexNumberElement(1));
		context.addToCurrentParameter(LatexStaticElements.CLOSE);
		context.addToCurrentParameter(LatexStaticElements.MULTIPLY);
	}
	
	@Override
	public boolean shouldRemoveMinus(LatexElement next) {
		if (next.isNumber()) return false;
		return true;
	}
	
	@Override
	public void arithOperationExtraction(ArithmaticOpContext context) {
		if (context.nestingLevel > 0 || context.operatorExtracted != Operator.Add) super.arithOperationExtraction(context);
		else {
			if (!context.isFirstElement) context.createNextParameter();
			addMinus(context);
		}
	}

}
