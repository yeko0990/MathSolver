package yekocalc;

public class PolynomEquationIsSingleDegree extends PolynomEquationStepState {
	private PolynomEquationStepState singleDegree;
	private PolynomEquationStepState notSingleDegree;
	
	public PolynomEquationIsSingleDegree(PolynomEquationStepState ifSingleDeg, PolynomEquationStepState notSingleDeg) {
		singleDegree = ifSingleDeg;
		notSingleDegree = notSingleDeg;
	}

	@Override
	public PolynomEquationStepState advance(SolveableManipulateBehavior manipulator, PolynomEquation pe) {
		return super.polyManipulator(manipulator).isSingleDegreeEquation(pe) ? singleDegree : notSingleDegree;
	}

}
