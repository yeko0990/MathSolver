package yekocalc;

public class PolynomEquationIsolateDegree extends PolynomEquationStepState {
	private PolynomEquationStepState ifWasIsolated;
	private Side isolateToSide;
	
	public PolynomEquationIsolateDegree(Side isolateTo, PolynomEquationStepState transitionIfWasIsolated) {
		ifWasIsolated = transitionIfWasIsolated;
		isolateToSide = isolateTo;
	}

	@Override
	public PolynomEquationStepState advance(SolveableManipulateBehavior manipulator, PolynomEquation pe) {
		PolynomEquationManipulateBehavior polyManipulate = super.polyManipulator(manipulator);
		return polyManipulate.tryIsolateSingleDegree(pe, isolateToSide) ? new PolynomEquationStepDone() : ifWasIsolated;
	}

}
