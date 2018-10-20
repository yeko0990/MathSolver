package yekocalc;

public class PolynomEquationCantSolve extends PolynomEquationStepState {

	public PolynomEquationCantSolve() {
		
	}

	@Override
	public PolynomEquationStepState advance(SolveableManipulateBehavior manipulator, PolynomEquation pe) {
		throw new CannotSolveException();
	}

}
