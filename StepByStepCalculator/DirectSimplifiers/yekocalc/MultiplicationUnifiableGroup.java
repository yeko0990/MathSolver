package yekocalc;

import java.util.List;

public class MultiplicationUnifiableGroup extends UnifiableGroup {

	public MultiplicationUnifiableGroup(Symbol unifiable, int firstIndexP) {
		super(unifiable, firstIndexP);
	}
	
	private Symbol argumentsAddition() {
		Addition buff = new Addition();
		List<Symbol> buffParams = buff.getParams();
		///TODO OPTIMIAZATION- Maybe we dont need deep copy
		for (Argument nextArg : argumentsGroup) buffParams.add(nextArg.arg.deepCopy());
		return buff;
	}

	@Override
	public Symbol internalUnify() {
		Multiplication unified = new Multiplication();
		List<Symbol> params = unified.getParams();
		params.add(unifiable);
		params.add(argumentsAddition());
		return unified;
	}

}
