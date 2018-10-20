package yekocalc;

public class EquationManipulateBehavior {
	PolynomEquationManipulateBehavior polyBehavior;
	
	public EquationManipulateBehavior(PolynomEquationManipulateBehavior behavior) {
		polyBehavior = behavior;
	}
	public EquationManipulateBehavior() {
		polyBehavior = new PolynomEquationManipulateBehavior();
	}
	
	public PolynomEquationManipulateBehavior getPolyManipulator() {
		return polyBehavior;
	}
	
	//public void setEquation(Equation eq) {
	//	polyBehavior.setEquation(eq);
	//}
	
	/*public EquationManipulateBehavior copy() {
		return new EquationManipulateBehavior(polyBehavior.copy());
	}*/
	
	/*public void isolate(Symbol sym) {
		equation.isolate(sym);
	}
	
	public boolean simplify(Side side) {
		return equation.simplify(side);
	}
	
	public boolean simplifyNext(Side side) {
		return equation.simplifyNext(side);
	}*/
}
