package yekocalc;

public class SolveableManipulateBehavior {
	private EquationManipulateBehavior equationManipulate;
	private EquationSetManipulateBehavior equationSetManipulate;
	
	public SolveableManipulateBehavior(EquationManipulateBehavior eqManipulate, EquationSetManipulateBehavior eqSetManipulate) {
		equationManipulate = eqManipulate;
		equationSetManipulate = eqSetManipulate;
	}
	public SolveableManipulateBehavior() {
		equationManipulate = new EquationManipulateBehavior();
	}
	
	public EquationManipulateBehavior getEquationManipulator() {
		return equationManipulate;
	}
	
	public EquationSetManipulateBehavior getEquationSetManipulator() {
		return equationSetManipulate;
	}
}
