package yekocalc;

import java.util.LinkedList;
import java.util.List;

public class EquationOrSet implements Solveable {
	private SolveableManipulateBehavior manipulate;
	private List<Equation> equations;
	private int currentlySolving;
	private boolean startedSolving;
	private List<EquationSolution> solutions;
	
	public EquationOrSet(SolveableManipulateBehavior manipulate) {
		this.equations = new LinkedList<Equation>();
		solutions = new LinkedList<EquationSolution>();
		currentlySolving = 0;
		
		this.manipulate = manipulate;
	}
	
	public EquationOrSet(List<Equation> equationsList, SolveableManipulateBehavior manipulate) {
		this(manipulate);
		for (Equation eq : equationsList) this.equations.add(eq);
	}
	
	public EquationOrSet(SolveableManipulateBehavior manipulate, Equation...equationsArr) {
		this(manipulate);
		Equation eq;
		for (int i = 0; i < equationsArr.length; i++) {
			eq = equationsArr[i];
			equations.add(eq);
		}
	}

	public void setManipulateBehavior(SolveableManipulateBehavior newManipulate) {
		this.manipulate = newManipulate;
	}
	
	
	
	@Override
	public SolveableManipulateBehavior manipulateBehavior() {
		return manipulate;
	}

	@Override
	public String printSolveable(VariableIDTable table) {
		// TODO Implement this
		return null;
	}

	private boolean doneSolving() {
		return solutions.size() == equations.size();
	}
	
	@Override
	public Solution reachedSolution() {
		if (doneSolving()) return new EquationSetSolution(solutions);
		else return null;
	}

	private void continueSolving() {
		Equation currentEq = equations.get(currentlySolving);
		EquationSolution tryGetSolution = currentEq.reachedSolution();
		
		if (tryGetSolution != null) {
			solutions.add(tryGetSolution);
			currentlySolving++;
			if (doneSolving()) this.manipulate.getEquationSetManipulator().onEndSolving();
			else this.manipulate.getEquationSetManipulator().onNextEquation(equations.get(currentlySolving), currentlySolving);
		}
	
		currentEq.stepSolve();
	}
	
	@Override
	public Solveable stepSolve() {
		if (!startedSolving) {
			this.manipulateBehavior().getEquationSetManipulator().onBeginSolving(this.equations);
			startedSolving = true;
			
			if (equations.size() > 0)
				this.manipulateBehavior().getEquationSetManipulator().onFirstEquation(equations.get(0));
		}
		if (!doneSolving()) {
			continueSolving();
		}
		return this;
	}

	@Override
	public Solveable fullSolve() {
		return new FullSolve().fullSolve(this);
	}

}
