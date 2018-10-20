package yekocalc;


public class PlusMinusEquationSolution extends EquationSolveBehavior {

	public PlusMinusEquationSolution(Equation equationP) {
		super(equationP);
	}
	
	private Equation createEquation(Symbol splittedSideSym, Symbol notSplittedSideSym, Side splittedSide) {
		///TODO Maybe we should deep-copy splitted side? Probably not because deep-copy is done at the splitting of the plus-minus.
		Equation eq = splittedSide == Side.Left ? new Equation( new Expression(splittedSideSym), new Expression(notSplittedSideSym.deepCopy())) :
												  new Equation(new Expression(notSplittedSideSym.deepCopy()), new Expression(splittedSideSym));
		eq.setManipulateBehavior(super.equation.manipulateBehavior());
		eq.setSolvedVariable(super.equation.targetVariable());
		return eq;
	}
	
	private EquationOrSet splitEquation(Pair<Symbol> eqs, Symbol otherSide, Side splittedSide) {
		Equation eq1 = createEquation(eqs.o1, otherSide, splittedSide);
		Equation eq2 = createEquation(eqs.o2, otherSide, splittedSide);
		
		return new EquationOrSet( super.equation.manipulateBehavior(), eq1, eq2);
	}
	
	@Override
	public Solveable nextStep() {
		///TODO IMPORTANT- move the simplification manipulation OUT OF PolynomManipulator TO EquationManipulator.
		if (!super.equation.manipulateBehavior().getEquationManipulator().getPolyManipulator().areSidesFullySimplified(new PolynomEquation(super.equation))) return super.equation;
		else {
			TryConvertPlusMinus convert = new TryConvertPlusMinus();
			Pair<Symbol> conversion;
			conversion = convert.tryConvert(super.equation.getLeft().getStoredSym());
			if (conversion != null) return splitEquation(conversion, super.equation.getRight().getStoredSym(), Side.Left);
			conversion = convert.tryConvert(super.equation.getRight().getStoredSym());
			if (conversion != null)  return splitEquation(conversion, super.equation.getLeft().getStoredSym(), Side.Right);
			
			throw new CannotSolveException();
		}
	}

}
