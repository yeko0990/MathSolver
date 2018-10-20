package yekocalc;
import java.util.*;

public class PowerUnifiableGroup extends UnifiableGroup {

	public PowerUnifiableGroup(Symbol unifiableP, int firstIndexP) {
		super(unifiableP, firstIndexP);
	}

	public PowerUnifiableGroup(Symbol unifiableP) {
		super(unifiableP);
	}

	private Addition argsAddition() {
		Addition addArgs = new Addition();
		List<Symbol> addArgsParams = addArgs.getParams();
		for (Argument nextArg : argumentsGroup) {
			addArgsParams.add(nextArg.arg);
		}
		return addArgs;
	}
	
	@Override
	protected Symbol internalUnify() {
		Power unified = new Power(unifiable, argsAddition());
		return unified.standarizedForm();
	}

}
