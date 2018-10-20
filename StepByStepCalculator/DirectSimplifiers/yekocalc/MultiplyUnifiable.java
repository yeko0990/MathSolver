package yekocalc;
import java.util.*;

public class MultiplyUnifiable extends Unifiable {
	
	public MultiplyUnifiable(Symbol unifiableP, Symbol unifiableFunctionP, int indexP) {
		super(unifiableP, unifiableFunctionP, indexP);
	}

	private Argument copyMultiplyArgument() {
		/*Symbol arg = unifiableFunction.deepCopy();
		List<Symbol> argParams = arg.getParams();
		argParams.remove(unifiable);
		return arg;*/
		Symbol testing = unifiableFunction.getCoefficient(unifiable);
		return new Argument(unifiableFunction.getCoefficient(unifiable), unifiableFunction);
		
	}
	
	@Override
	public Argument getRepetitionArgument() {
		if (unifiableFunction.getType() == SymType.Multiplication) {
			List<Symbol> originParams = unifiableFunction.getParams();
			if (originParams.size() == 1) return new Argument(NumberSym.doubleSymbol(1), unifiableFunction);
			else return copyMultiplyArgument();
			
		}
		else return new Argument(NumberSym.doubleSymbol(1), unifiableFunction);
	}

}
