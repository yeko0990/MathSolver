package yekocalc;

public class PolynomEquationOpenForm extends PolynomEquationStepState {
	private PolynomEquationStepState transitionIfOpened;
	
	public PolynomEquationOpenForm(PolynomEquationStepState transitionIfOpenedP) {
		transitionIfOpened = transitionIfOpenedP;
	}

	@Override
	public PolynomEquationStepState advance(SolveableManipulateBehavior manipulator, PolynomEquation pe) {
		PolynomEquationManipulateBehavior manipulate = super.polyManipulator(manipulator);
		if (manipulate.areOpenForm(pe)) return transitionIfOpened;
		else {
			manipulate.openForm(pe);
			return new PolynomEquationStepDone();
		}
	}

}
