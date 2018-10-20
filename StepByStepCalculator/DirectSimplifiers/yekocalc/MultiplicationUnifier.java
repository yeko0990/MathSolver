package yekocalc;
import java.util.*;

///USED AS AN ADDITION SIMPLIFIER
public class MultiplicationUnifier extends RepeatingFunctionUnifier {
	private Addition addition;
	
	public MultiplicationUnifier(Addition addition) {
		this.addition = addition;
	}
	
	@Override
	public boolean isLightSimplification() {
		return false;
	}
	
	@Override
	public List<Symbol> getParameters() {
		return addition.getParams();
	}
	
	@Override
	public void setParameters(List<Symbol> newParams) {
		addition.setParams(newParams);
	}
	
	@Override
	protected UnifiableGroup createGroup(Symbol unifiable, int index) {
		return new MultiplicationUnifiableGroup(unifiable, index);
	}
	
	@Override
	protected Unifiable getEffectiveUnifiable(Symbol sym, int index) {
		/*List<Unifiable> buff = new LinkedList<Unifiable>();
		List<Symbol> params = sym.getParams();
		MultiplyUnifiable nextUni;
		for (Symbol p : params) {
			nextUni = toEffectiveUnifiable(p);
			if (nextUni != null) buff.add(nextUni);
		}
		return buff;
		*/
		if (sym.getType() == SymType.Multiplication) {
			/*Symbol unifiable = isEffectiveMultiUnifiable(sym);
			if (unifiable == null) return null;
			return new MultiplyUnifiable(unifiable, sym, index);*/
			return getEffectiveMultiUnifiable(sym, index);
		}
		else {
			return getOtherUnifiable(sym, index);
		}
	}
	
	/*protected MultiplyUnifiable toEffectiveUnifiable(Symbol sym) {
		if (sym.getType() == SymType.Multiplication) {
			Symbol unifiable = isEffectiveMultiUnifiable(sym);
			if (unifiable == null) return null;
			return new MultiplyUnifiable(unifiable, sym);
		}
		else {
			return getOtherUnifiable(sym);
		}
	}*/
	
	private Unifiable getEffectiveMultiUnifiable(Symbol sym, int index) {
		List<Symbol> symParams = sym.getParams();
		Multiplication newMul = new Multiplication(new LinkedList<Symbol>(symParams));
		List<Symbol> newMulParams = newMul.getParams();
		Iterator<Symbol> iterate = newMulParams.iterator();
		while (iterate.hasNext()) if (isNumber(iterate.next())) iterate.remove();
		
		if (newMulParams.size() == 0) return null;
		if (newMulParams.size() == 1) return new MultiplyUnifiable(newMul.getParams().get(0), sym, index);
		return new MultiplyUnifiable(newMul, sym, index); 
	}
	
	/*private Symbol isEffectiveMultiUnifiable(Symbol sym) {
		Symbol unifiable = null;
		List<Symbol> params = sym.getParams();
		for (Symbol next : params) {
			if (isNumber(next)) continue;
			else if (unifiable != null) return null;
			else unifiable = next;
		}
		return unifiable;
	}*/
	
	private MultiplyUnifiable getOtherUnifiable(Symbol sym, int index) {
		if (isNumber(sym)) return null;
		return new MultiplyUnifiable(sym, sym, index);
	}
	
	private boolean isNumber(Symbol unifiable) {
		return unifiable.getIdentifier().isTranslatedNumber().getIsNum() ||
				unifiable.isImplicatedNumber() ||
				unifiable.isRawNumber();
	}
	
	/*private boolean isEffectiveUnifiable(Symbol unifiable) {
		if (isNumber(unifiable)) return false;
		else if (unifiable.getType() == SymType.Multiplication)) return isEffectiveMultiUnifiable()
	}
	private boolean isEffectiveMultiUnifiable(Symbol unifiable) { 
		
	}
	private boolean isEffectiveOtherUnifiable(Symbol unifiable) { 
		boolean notNumber = !unifiable.symId.isTranslatedNumber().getIsNum();
	}*/
	
	//@Override
	//protected Symbol unify(List<Unifiable> toUnify) {
		/*List<Symbol> originalParams = originalSym.getParams();
		List<Symbol> newParams = new LinkedList<Symbol>();
		List<Symbol> coefficients = new LinkedList<Symbol>();
		
		Symbol nextCoefficient;
		Symbol nextParam;
		for (int i = 0; i < originalParams.size(); i++) {
			nextParam = originalParams.get(i);
			nextCoefficient = nextParam.getCoefficient(toUnify);
			
			if (nextCoefficient.isZero()) newParams.add(nextParam.deepCopy());
			else coefficients.add(nextCoefficient);
		}
		
		newParams.add(new Multiplication(new Addition(coefficients), toUnify));
		
		originalSym.setParams(newParams);
		*/
	//	}
}
