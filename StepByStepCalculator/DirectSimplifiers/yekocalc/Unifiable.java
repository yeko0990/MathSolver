package yekocalc;

///UNIFIABLE is an argument of a REPETITON FUNCTION that may be unified with an identical UNIFIABLE.
//Example: 5x + 2x -> x is a unifiable, the two unifiables may be unified into 7x.
public abstract class Unifiable {
	protected Symbol unifiable;
	protected Symbol unifiableFunction; //The symbol in which the unifiable is an argument.
	protected int index; //Index of unifiableFunction in the parameters
	
	public Unifiable(Symbol unifiableP, Symbol unifiableFunctionP, int indexP) {
		unifiable = unifiableP;
		unifiableFunction = unifiableFunctionP;
		index = indexP;
	}
	
	public abstract Argument getRepetitionArgument(); //Returns the other argument\s (other than the unifiable)

	public Symbol getUnifiable() {
		return unifiable;
	}
	
	public Symbol getFunction() {
		return unifiableFunction;
	}
	
	public int getIndex() {
		return index;
	}
	
}
