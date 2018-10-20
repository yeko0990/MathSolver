package yekocalc;

import java.util.List;

import latexpression.VariableIDTable;

public class PrintEquationSet extends EquationSetManipulateBehavior {
	private VariableIDTable table;
	private StringBuilder build;
	
	public PrintEquationSet(StringBuilder str, VariableIDTable table) {
		build = str;
		this.table = table;
	}
	
	public void setOutput(StringBuilder output) {
		this.build = output;
	}
	
	public void print(String str) {
		build.append(str);
	}

	
	public void println(String str) {
		this.print(str);
		this.print("\n");
	}
	
	private void printEquation(Equation eq) {
		println("$$" + eq.printSolveable(table) + "$$");
	}
	
	@Override
	public void onBeginSolving(List<Equation> set) {
		if (set.size() == 0) return;
		print("Split the solution to these equations: $$");
		print(set.get(0).printSolveable(table));
		for (int i = 1; i < set.size(); i++) {
			print("\\,,");
			print(set.get(i).printSolveable(table));
		}
		println("$$");
		
	}
	
	@Override
	public void onFirstEquation(Equation firstEq) {
		print("Solve the first equation: ");
		printEquation(firstEq);
	}
	
	@Override
	public void onNextEquation(Equation next, int equationIndex) {
		print("Solve the next equation: ");
		printEquation(next);
	}
	@Override
	public void onEndSolving() {
		
	}

}
