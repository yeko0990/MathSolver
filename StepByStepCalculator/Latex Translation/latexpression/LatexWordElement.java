package latexpression;

///A word element is a
public abstract class LatexWordElement extends LatexElement {
	public LatexWordElement() {
		super(new ExpectingOperatorPutMultiplies());
	}
	
	@Override
	public final boolean isWord() {
		return true;
	}
	
}
