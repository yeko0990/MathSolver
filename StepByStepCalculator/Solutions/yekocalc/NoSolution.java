package yekocalc;

import latexpression.VariableIDTable;

public class NoSolution extends EquationSolution {
	
	public NoSolution(Variable targetVar) {
		super(targetVar, null);
	}

	@Override
	public String printLatex(VariableIDTable idTable) {
		return "No solution for " + idTable.getName(super.solvedFor.id());
	}

}
