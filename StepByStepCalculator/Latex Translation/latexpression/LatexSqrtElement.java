package latexpression;

public class LatexSqrtElement extends LatexElement {
	private LatexElementList base, rootBy;
	public LatexSqrtElement(LatexElementList base, LatexElementList rootBy) {
		super(new ExpectingOperatorPutMultiplies());
		this.base = base;
		this.rootBy = rootBy;
	}
	
	@Override
	public boolean isWord() {
		return true;
	}
	
	@Override
	public void wordExtraction(GetWordContext context) {
		context.wordExpression = new LatexRoot(base.evaluate(), rootBy.evaluate());
	}

}
