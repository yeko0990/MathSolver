package latexpression;

public class LatexOperatorElement extends LatexElement {
	public final Operator op;
	public final boolean extractingOnce; ///True if extraction of this operator is single- power, for example.
	
	public LatexOperatorElement(Operator op, boolean extractingOnce) {
		super(new NeverPutMultiplies());
		this.op = op;
		this.extractingOnce = extractingOnce;
	}
	
	@Override
	public boolean isOperator(Operator op) {
		return this.op == op;
	}
	
	@Override
	public void arithOperationExtraction(ArithmaticOpContext context) {
		boolean extracted = false;
		if (context.nestingLevel > 0 || (this.extractingOnce && extracted)) {
			super.arithOperationExtraction(context);
			return;
		}
		
		if (context.operatorExtracted == this.op) {
			extracted = true;
			context.createNextParameter();
		}
		else super.arithOperationExtraction(context);
	}

}
