package yekocalc;
import java.util.*;

public class FractionTranslatedNumberIdentifier extends TranslatedNumberIdentifier {

	public FractionTranslatedNumberIdentifier(Symbol sym) {
		super(sym);
	}

	private NumberInfo getNumber(NumberInfo numerator, NumberInfo denominator) {
		if (!numerator.getIsNum() || !denominator.getIsNum()) return NumberInfo.notNumber();
		
		return new NumberInfo(numerator.getNum().div(denominator.getNum()));
	}
	
	@Override
	public NumberInfo isTranslatedNumber() {
		Symbol numerator = super.sym.getParams().get(0);
		Symbol denominator = super.sym.getParams().get(1);
		return getNumber(numerator.getIdentifier().isTranslatedNumber(), denominator.getIdentifier().isTranslatedNumber());
	}

}
