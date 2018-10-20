package yekocalc;

public class PolynomEquationIsSecondDegree extends PolynomEquationStepState {
	private PolynomEquationStepState ifSecondDeg;
	private PolynomEquationStepState ifNotSecondDeg;
	
	public PolynomEquationIsSecondDegree(PolynomEquationStepState ifSecDeg, PolynomEquationStepState ifNotSecDeg) {
		this.ifSecondDeg = ifSecDeg;
		this.ifNotSecondDeg = ifNotSecDeg;
	}

	@Override
	public PolynomEquationStepState advance(SolveableManipulateBehavior manipulator, PolynomEquation pe) {
		PolynomEquationManipulateBehavior polyManipulate = super.polyManipulator(manipulator);
		return polyManipulate.isSecondDegree(pe) ? this.ifSecondDeg : this.ifNotSecondDeg;
	}

}
