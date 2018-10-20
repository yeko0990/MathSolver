package yekocalc;

public class PlusMinusTranslatedNumber extends TranslatedNumberIdentifier {

	public PlusMinusTranslatedNumber(Symbol sP) {
		super(sP);
	}

	@Override
	public NumberInfo isTranslatedNumber() {
		NumberInfo parameterInf = super.sym.getParams().get(0).getIdentifier().isTranslatedNumber();
		if (parameterInf.getIsNum()) return parameterInf;
		else return NumberInfo.notNumber();
	}

}
