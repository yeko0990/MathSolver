package yekocalc;

import latexpression.VariableIDTable;
import yekocalc.Solution;
import yekocalc.Variable;

public class EveryRealNumberSolution extends EquationSolution {
	
	public EveryRealNumberSolution(Variable targetVariable) {
		super (targetVariable, null);
	}

	@Override
	public String printLatex(VariableIDTable idTable) {
		return "Every real " + idTable.getName(super.solvedFor.id());
	}

}
