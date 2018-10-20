package yekocalc;

public class PolynomEquationRootSides extends PolynomEquationStepState {
	//private PolynomEquation ifNotActed;
	
	public PolynomEquationRootSides() {
		
	}

	@Override
	public PolynomEquationStepState advance(SolveableManipulateBehavior manipulator, PolynomEquation pe) {
		PolynomEquationManipulateBehavior polyManipulate = super.polyManipulator(manipulator);
		polyManipulate.rootByVariableDegree(pe);
		return new PolynomEquationStepDone();
	}

}
