package yekocalc;

///When one side is a multiplication of the target variable, divide sides by the coefficient of the target variable.
///example: 5x = 10 -> divide by 5 -> x = 2
public class PolynomEquationDivideByVariableCoefficient extends PolynomEquationStepState {
	private PolynomEquationStepState ifNotActed;
	
	public PolynomEquationDivideByVariableCoefficient(PolynomEquationStepState ifNotActed) {
		this.ifNotActed = ifNotActed;
	}

	@Override
	public PolynomEquationStepState advance(SolveableManipulateBehavior manipulator , PolynomEquation pe) {
		PolynomEquationManipulateBehavior polyManipulate = polyManipulator(manipulator);
		return polyManipulate.tryDivideVariableByCoefficient(pe) ? new PolynomEquationStepDone() : ifNotActed;
	}

}
