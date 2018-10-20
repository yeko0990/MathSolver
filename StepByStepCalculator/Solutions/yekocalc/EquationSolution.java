package yekocalc;

import latexpression.VariableIDTable;

public class EquationSolution implements Solution {
	Variable solvedFor;
	Symbol solution;
	
	public EquationSolution(Variable target, Symbol targetSolution) {
		solvedFor = target;
		solution = targetSolution;
	}

	@Override
	public String printLatex(VariableIDTable idTable) {
		StringBuilder build = new StringBuilder();
		build.append(solvedFor.latexString(idTable));
		build.append(" = ");
		build.append(solution.latexString(idTable));
		return build.toString();
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		if (o instanceof EquationSolution) {
			EquationSolution asEqSol = (EquationSolution) o;
			return solvedFor.equals(asEqSol.solvedFor) && solution.equals(asEqSol.solution);
		}
		else return false;
	}

}
