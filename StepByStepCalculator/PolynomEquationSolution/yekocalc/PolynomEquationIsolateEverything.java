package yekocalc;

public class PolynomEquationIsolateEverything extends PolynomEquationStepState {
	private PolynomEquationStepState ifNotActed;
	private Side toSide;
	
	public PolynomEquationIsolateEverything(PolynomEquationStepState ifNotActed, Side toSide) {
		this.ifNotActed = ifNotActed;
		this.toSide = toSide;
	}

	@Override
	public PolynomEquationStepState advance(SolveableManipulateBehavior manipulator, PolynomEquation pe) {
		PolynomEquationManipulateBehavior polyM = super.polyManipulator(manipulator);
		return polyM.tryIsolateEverything(pe, Side.Left) ? new PolynomEquationStepDone() : ifNotActed;
	}

}
