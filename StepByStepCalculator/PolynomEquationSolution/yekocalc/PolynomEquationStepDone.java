package yekocalc;

public class PolynomEquationStepDone extends PolynomEquationStepState {

	@Override
	public boolean isFinishedState() {
		return true;
	}

	@Override
	public PolynomEquationStepState advance(SolveableManipulateBehavior mani, PolynomEquation pe) {
		return null;
	}

}
