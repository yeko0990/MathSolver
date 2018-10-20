package latexpression;

///LatexOperators are Immutable, so a static version of each one is more efficient.
public final class LatexStaticElements {
	public static final LatexOperatorElement ADDITION = new LatexOperatorElement(Operator.Add, false);
	public static final LatexMinusElement MINUS = new LatexMinusElement();
	public static final LatexOperatorElement MULTIPLY = new LatexOperatorElement(Operator.Mul, false);
	public static final LatexOperatorElement DIVISION = new LatexOperatorElement(Operator.Div, true);
	public static final LatexOperatorElement POWER = new LatexOperatorElement(Operator.Pow, true);
	public static final LatexOpenElement OPEN = new LatexOpenElement();
	public static final LatexCloseElement CLOSE = new LatexCloseElement();



}
