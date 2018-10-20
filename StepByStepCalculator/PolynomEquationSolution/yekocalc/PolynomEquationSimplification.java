package yekocalc;

public class PolynomEquationSimplification extends PolynomEquationStepState {
	private PolynomEquationStepState transitionIfSimplified;
	
	public PolynomEquationSimplification(PolynomEquationStepState ifSimplified) {
		transitionIfSimplified = ifSimplified;
	}
	
	/*private boolean isFullySimplified(PolynomEquationManipulateBehavior polyBehave, Side side) {
		return !polyBehave.trySimplify(side);
	}*/
	
	@Override
	public PolynomEquationStepState advance(SolveableManipulateBehavior manipulator, PolynomEquation pe) {
		PolynomEquationManipulateBehavior polyManipulate = super.polyManipulator(manipulator);
		if (polyManipulate.areSidesFullySimplified(pe)) return transitionIfSimplified;
		else return new PolynomEquationStepDone();
	}

}
