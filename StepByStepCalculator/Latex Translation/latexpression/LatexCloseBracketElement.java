package latexpression;

public class LatexCloseBracketElement extends LatexElement {

	public LatexCloseBracketElement(PutMultipliesBehavior putMuls) {
		super(putMuls);
	}
	
	@Override
	public final boolean isDoneNesting() {
		return true;
	}

}
