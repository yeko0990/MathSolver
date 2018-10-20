package yekocalc;
import java.util.*;

///A group of the arguments of the same unifiable. example: 5x + 3x -> unifiable group where x is unifiable and
///group is {5, 3}
public abstract class UnifiableGroup {
	Symbol unifiable;
	LinkedList<Argument> argumentsGroup;
	
	int firstIndex; //The index of the first unifiable in the simplified symbol's parameters
	
	public UnifiableGroup(Symbol unifiableP, int firstIndexP) {
		unifiable = unifiableP;
		argumentsGroup = new LinkedList<Argument>();
		firstIndex = firstIndexP;
	}
	
	public UnifiableGroup(Symbol unifiableP) {
		this(unifiableP, 0);
	}
	
	public List<Argument> group() {
		return argumentsGroup;
	}
	
	public void add(Argument newArg) {
		argumentsGroup.add(newArg);
	}
	
	public void setFirstIndex(int newInd) {
		firstIndex = newInd;
	}
	
	public int getFirstIndex() {
		return firstIndex;
	}
	
	public final Symbol unify() {
		Symbol ret = internalUnify();
		return ret.simplifiedForm(new MutableBoolean());	
	}
	
	protected abstract Symbol internalUnify(); //Unifies the arguments. SHOULD RETURN A STANDARIZED SYMBOL

}
