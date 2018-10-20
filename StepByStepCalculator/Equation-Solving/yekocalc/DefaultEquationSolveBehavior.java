package yekocalc;

public class DefaultEquationSolveBehavior extends EquationSolveBehavior {

	public DefaultEquationSolveBehavior(Equation equationP) {
		super(equationP);
	}

	@Override
	public Solveable nextStep() {
		boolean leftSimp = super.equation.simplify(Side.Left);
		boolean rightSimp = super.equation.simplify(Side.Right);
		
		if (leftSimp || rightSimp) return super.equation;
		else throw new CannotSolveException();
	}

}
