package yekocalc;

public class PolynomEquationQuadraticFormula extends PolynomEquationStepState {
	
	public PolynomEquationQuadraticFormula() {
		
	}

	@Override
	public PolynomEquationStepState advance(SolveableManipulateBehavior manipulator, PolynomEquation pe) {
		PolynomEquationManipulateBehavior polyManipulate = super.polyManipulator(manipulator);
		polyManipulate.applyQuadraticFunction(pe);
		return new PolynomEquationStepDone();
	}

}
