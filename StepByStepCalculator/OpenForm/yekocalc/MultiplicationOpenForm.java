package yekocalc;
import java.util.*;

public class MultiplicationOpenForm extends OpenForm {
	private Symbol sym;
	
	private int indexOfNextAddition() {
		List<Symbol> params = sym.getParams();
		for (int i = 0; i < params.size(); i++) {
			if (params.get(i).getType() == SymType.Addition) return i;
		}
		return -1;
	}
	
	private List<Symbol> noAdditionParams(int additionIndex) {
		List<Symbol> newParams = new LinkedList<Symbol>();
		ListUtils.copyWithoutIndex(additionIndex, sym.getParams(), newParams);
		return newParams;
	}
	
	///TODO Deep-copies may be performance-heavy. Consider optimizing (may not be possible)
	private Symbol createMultiplication(Symbol additionParam, List<Symbol> extractedParams) {
		Symbol multi = new Multiplication(extractedParams).deepCopy();
		multi.getParams().add(additionParam);
		return multi;
	}
	
	/// TODO IMPORTANT: newParams and additionParams are SHALLOWED COPIED. This might cause problems (if one of the parameters are simplified, for example).
	///		 consider changing to deep copy
	private Symbol openAddition() {
		int additionIndex = indexOfNextAddition();
		if (additionIndex == -1) throw new IllegalArgumentException(); //Probably wont ever throw this, just here for debugging
		List<Symbol> newParams = noAdditionParams(additionIndex);
		List<Symbol> additionParams = sym.getParams().get(additionIndex).getParams();
		List<Symbol> multiplications = new LinkedList<Symbol>();
		
		for (Symbol nextAdditionParam : additionParams) multiplications.add(createMultiplication(nextAdditionParam, newParams));
		return new Addition(multiplications);
		
	}
	
	///Returns true if took action
	private boolean openSymParameters() {
		List<Symbol> params = this.sym.getParams();
		Symbol nextP;
		boolean didAction = false;
		MutableBoolean nextResult = new MutableBoolean();
		for (int i =0; i < params.size(); i++) {
			nextP = params.get(i);
			params.set(i, nextP.openForm(nextResult));
			if (nextResult.value()) didAction = true;
		}
		return didAction;
	}
	
	@Override
	public Symbol openForm(Symbol symP, MutableBoolean tookAction) {
		sym = symP;
		tookAction.set(false);
		if (isOpen(sym)) {
			sym = sym.standarizedForm();
			return sym;
		}
		
		tookAction.set(true);
		openSymParameters();
		Symbol opened = openAddition();
		sym = opened;
		sym = sym.standarizedForm();
		sym = sym.openForm(); //this function only opens one addition, so it should call itself again to make sure
							  //there are no more additions.
		return sym;
	}

	private boolean everyParameterOpen() {
		for (Symbol nextP : sym.getParams()) if (!nextP.isOpenForm()) return false;
		return true;
	}
	
	@Override
	public boolean isOpen(Symbol symP) {
		sym = symP;
		return indexOfNextAddition() == -1 && everyParameterOpen();
	}

}
