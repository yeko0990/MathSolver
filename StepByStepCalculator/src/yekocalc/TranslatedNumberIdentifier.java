package yekocalc;
import java.util.*;

///An identifier which MAY detect a symbol which can be converted to a number
public abstract class TranslatedNumberIdentifier {
	protected Symbol sym;
	public TranslatedNumberIdentifier(Symbol sP) {
		sym = sP;
	}
	public abstract NumberInfo isTranslatedNumber();
}

///A prototype of a translatednumberidentifier which first check whether all parameters are numbers, than
///perform an action on these parameters (example- multiplies them) and returns a number info result.
abstract class AllParametersNumber extends TranslatedNumberIdentifier {
	public AllParametersNumber(Symbol sP) {
		super(sP);
	}
	///Returns true if and only if all of sym parameters are translated numbers and there is at least 1 parameter.
	private boolean symParametersNumber(List<NumberInfo> outList) {
		NumberInfo nextNum;
		List<Symbol> symParams = sym.getParams();
		for (Symbol nextParam : symParams) {
			nextNum = nextParam.getIdentifier().isTranslatedNumber();
			if (!nextNum.getIsNum()) return false;
			else outList.add(nextNum);
		}
		if (outList.size() == 0) return false;
		return true;
	}
	
	protected abstract NumberInfo handleParameters(List<NumberInfo> params);
	
	@Override
	public final NumberInfo isTranslatedNumber() {
		List<NumberInfo> paramsInfo = new LinkedList<NumberInfo>();
		if (!symParametersNumber(paramsInfo)) return NumberInfo.notNumber();
		else return handleParameters(paramsInfo);
	}
}
