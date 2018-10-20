package yekocalc;

import yekocalc.Solution;
import yekocalc.Variable;
import yekocalc.VariableIDTable;

public class EveryRealNumberSolution extends EquationSolution {
	
	public EveryRealNumberSolution(Variable targetVariable) {
		super (targetVariable, null);
	}

	@Override
	public String printLatex(VariableIDTable idTable) {
		return "Every real " + idTable.getName(super.solvedFor.id());
	}

}
