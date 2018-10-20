package yekocalc;

public class PolynomEquationSolution extends EquationSolveBehavior {
	private final PolynomEquationStepState cannotSolve = new PolynomEquationCantSolve();
	
	private final PolynomEquationStepState finished = new PolynomEquationStepDone();
	private final PolynomEquationStepState quadraticFunction = new PolynomEquationQuadraticFormula();
	private final PolynomEquationStepState isolateEverythingToLeft = new PolynomEquationIsolateEverything(quadraticFunction, Side.Left);
	private final PolynomEquationStepState isSecondDegree = new PolynomEquationIsSecondDegree(isolateEverythingToLeft, cannotSolve);
	
	private final PolynomEquationStepState rootByVariableExponent = new PolynomEquationRootSides();
	private final PolynomEquationStepState rootIfNotFirstDegree = new PolynomEquationIsFirstDegree(finished, rootByVariableExponent);
	
	private final PolynomEquationStepState divideVar = new PolynomEquationDivideByVariableCoefficient(rootIfNotFirstDegree);
	private final PolynomEquationStepState isolateNumToRight = new PolynomEquationIsolateFreeNum(Side.Right,
																									divideVar);
	private final PolynomEquationStepState isolateDegreeToLeft = new PolynomEquationIsolateDegree(Side.Left,
																									isolateNumToRight);
	//private final PolynomEquationStepState isFirstDegree = new PolynomEquationIsFirstDegree(isolateDegreeToLeft, finished);
	private final PolynomEquationStepState isSingleDegree = new PolynomEquationIsSingleDegree(isolateDegreeToLeft, isSecondDegree);
	private final PolynomEquationStepState tryOpenForm = new PolynomEquationOpenForm(isSingleDegree);
	private final PolynomEquationStepState trySimplify = new PolynomEquationSimplification(tryOpenForm);

	private PolynomEquationStepState currentStep = trySimplify;
	
	public PolynomEquationSolution(Equation equationP) {
		super(equationP);
	}

	@Override
	public Solveable nextStep() {
		while (!currentStep.isFinishedState()) {
			currentStep = currentStep.advance(super.equation.manipulateBehavior(), new PolynomEquation(super.equation));
		}
		return super.equation;
	}

}
