package yekocalc;

///Returns whether the symbol is a raw number- does no effort to determine a possiblity
///to translate the symbol to a number.
public class NoTranslatedNumberIdentifier extends TranslatedNumberIdentifier {
	public NoTranslatedNumberIdentifier(Symbol sP) {
		super (sP);
	}
	
	@Override
	public NumberInfo isTranslatedNumber() {
		// TODO Auto-generated method stub
		return sym.getIdentifier().isRawNumber();
	}

}
