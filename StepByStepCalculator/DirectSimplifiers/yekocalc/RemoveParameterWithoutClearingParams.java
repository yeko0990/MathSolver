package yekocalc;
import java.util.*;

///A simplifiers that removes each parameter that is equal to a specified value UNLESS the parameter
///is the last parameter in the list.
public class RemoveParameterWithoutClearingParams implements DirectSimplifier {
	private Symbol sym;
	private final Symbol toRemove;
	
	public RemoveParameterWithoutClearingParams(Symbol sym, Symbol toRemoveP) {
		this.sym = sym;
		toRemove = toRemoveP;
	}
	
	///TODO Too broad- should make this class ABSTRACT, and make all current instances of this class STANDALONE CLASSES.
	///		Right now, we have no option to define only specific removings as a light simplification.
	@Override
	public boolean isLightSimplification() {
		return true;
	}


	@Override
	public boolean simplifyNext() {
		List<Symbol> params = sym.getParams();
		Iterator<Symbol> iterate = params.iterator();
		boolean didAction = false;
		while (iterate.hasNext()) {
			if (params.size() == 1) return didAction;
			if (iterate.next().equals(toRemove)) {
				didAction = true;
				iterate.remove();
			}
		}
		return false;
	}

}
