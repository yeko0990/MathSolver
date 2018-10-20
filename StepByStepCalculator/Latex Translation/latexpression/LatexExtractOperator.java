package latexpression;

import java.util.LinkedList;

public abstract class LatexExtractOperator extends LatexOperation {
	protected abstract Operator op();
	protected abstract void createExpression();
	protected abstract void addParameter(LatexEvaluatable param);
	protected abstract LatexExpression expression();

	@Override
	public boolean isOperation(LinkedList<LatexElement> elements) {
		int nesting = 0;
		for (LatexElement next : elements) {
			nesting += next.isNesting() ? 1 : 0;
			nesting -= next.isDoneNesting() ? 1 : 0;
			if (next.isOperator(this.op()) && nesting == 0) return true;
		}
		return false;
	}

	protected final ArithmaticOpContext iterateContext(LinkedList<LatexElement> elements) {
		ArithmaticOpContext context = new ArithmaticOpContext();
		context.isFirstElement = true;
		context.operatorExtracted = this.op();
		for (LatexElement next : elements) {
			if (next.isNesting()) context.nestingLevel++;
			if (next.isDoneNesting()) context.nestingLevel--;
			next.arithOperationExtraction(context);
			context.isFirstElement = false;
		}
		return context;
	}
	
	@Override
	public LatexExpression toExpression(LinkedList<LatexElement> elements) {
		ArithmaticOpContext context = iterateContext(elements);
		this.createExpression();
		for (LatexElementList param : context.parameters) this.addParameter(param);
		return this.expression();
	}

}
