package yekocalc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

///TODO Make EquationSolution save a possible range of values.
///		Currently, we might print the same solution twice... (x=2 or x=2)
///		Or we might print "Every real solution or x=4"...
public class EquationSetSolution implements Solution {
	private final String LATEX_SPACE = "\\,\\,\\,\\,";
	private List<EquationSolution> solutions;
	
	public EquationSetSolution(List<EquationSolution> solutions) {
		this.solutions = solutions;
	}
	
	public EquationSetSolution(EquationSolution...solutions) {
		this(new ArrayList<EquationSolution>(Arrays.asList(solutions)));
	}

	private void addSolution(StringBuilder build, VariableIDTable idTable, EquationSolution sol) {
		build.append(sol.printLatex(idTable));
	}
	
	private List<EquationSolution> noDuplicates() {
		LinkedList<EquationSolution> noDuplicated = new LinkedList<EquationSolution>();
		for (EquationSolution next : solutions) {
			if (!noDuplicated.contains(next)) noDuplicated.add(next);
		}
		return noDuplicated;
	}
	
	@Override
	public String printLatex(VariableIDTable idTable) {
		StringBuilder build = new StringBuilder();
		
		List<EquationSolution> uniques = noDuplicates();
		
		if (uniques.size() == 0) return "";
		addSolution(build, idTable, uniques.get(0));
		
		for (int i = 1 ; i < uniques.size(); i++) {
			build.append(LATEX_SPACE);
			build.append("Or");
			build.append(LATEX_SPACE);
			addSolution(build, idTable, uniques.get(i));
		}
		return build.toString();
		
	}

}
