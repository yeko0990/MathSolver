package latexpression;

public class LatexCloseElement extends LatexElement {
	
	public LatexCloseElement() {
		super(new ExpectingOperatorPutMultiplies()); //SHOULD BE IRRELEVANT AS CLOSE SIGN IS SKIPPED WHEN NESTED.
	}
	
	@Override
	public boolean isDoneNesting() {
		return true;
	}

}
