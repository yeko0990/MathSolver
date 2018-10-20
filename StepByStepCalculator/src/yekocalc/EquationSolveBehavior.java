package yekocalc;

public abstract class EquationSolveBehavior {
	protected Equation equation;
	
	public EquationSolveBehavior(Equation equationP) {
		equation = equationP;
	}
	
	public abstract Solveable nextStep();
}
