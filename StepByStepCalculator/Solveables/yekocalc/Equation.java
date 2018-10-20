package yekocalc;
import java.util.*;

import latexpression.LatexExpression;
import latexpression.VariableIDTable;

///TODO:
///Finish class
public class Equation implements Solveable {
	protected static SolveableManipulateBehavior defaultBehavior = null; //new SolveableManipulateBehavior(); ///TODO Define the default manipulator (only performs necessary action)

	Variable solveFor;
	Expression left, right;
	SolveableManipulateBehavior manipulator;
	
	EquationSolution explicitSolution;
	boolean decidedSolution;
	public Equation(Expression leftExp, Expression rightExp) {
		left = leftExp;
		right = rightExp;
		manipulator = defaultBehavior;
		
		decidedSolution = false;
		explicitSolution = null;
	}
	
	public Equation(Expression leftExp, Expression rightExp, Variable solveForVar) {
		this(leftExp, rightExp);
		setSolvedVariable(solveForVar);
	}
	
	public Equation(Expression leftExp, Expression rightExp, Variable solveForVar, SolveableManipulateBehavior manipulatorP) {
		this (leftExp, rightExp, solveForVar);
		this.setManipulateBehavior(manipulatorP);
	}
	
	public Expression getLeft() {
		return left;
	}
	
	public void setLeft(Symbol s) {
		this.left.setStoredSym(s);
	}
	
	public Expression getRight() {
		return right;
	}
	
	public void setRight(Symbol s) {
		this.right.setStoredSym(s);
	}
	
	public Expression getSide(Side side) {
		if (side == Side.Right) return getRight();
		else if (side == Side.Left) return getLeft();
		else throw new IllegalArgumentException();
	}
	
	public void setSide (Side side, Symbol s) {
		getSide(side).setStoredSym(s);
	}
	
	public Variable targetVariable() {
		return this.solveFor;
	}
	
	public void setSolvedVariable(Variable solveForVar) {
		solveFor = solveForVar;
	}
	
	private void divideSide(Expression exp, Symbol divideBy) {
		///TODO Change this to just fraction- only after simplifications for fractions are completed
		//exp.setStoredSym(new Multiplication(exp.getStoredSym(), new Fraction(NumberSym.intSymbol(1), divideBy.deepCopy())));
		exp.setStoredSym(new Fraction(exp.getStoredSym(), divideBy.deepCopy()));
	}
	
	public void divideSides(Symbol divideBy) {
		divideSide(left, divideBy);
		divideSide(right, divideBy);
	}
	
	private void rootSide(Side side, Symbol rootBy) {
		Expression sideExp = getSide(side);
		sideExp.setStoredSym(new Root(sideExp.getStoredSym(), rootBy.deepCopy())); ///TODO Maybe do not deep copy?
	}
	
	//private boolean evenExponent(Symbol exponent) {
	//	NumberInfo expoInf = exponent.getIdentifier().isRawNumber();
	//	if (expoInf.getIsNum() && expoInf.getNum().div(new CalcNumber(2)).isInteger()) return true;
	//	else return false;
	//}
	
	private void rootWithPlusMinus(Side sideToPlusMinus, Symbol rootBy) {
		Side notPlusMinusSide = sideToPlusMinus == Side.Left ? Side.Right : Side.Left;
		Symbol base = new GetSymbolPowerBase().getBase(this.getSide(notPlusMinusSide).getStoredSym());
		this.setSide(notPlusMinusSide, base);
		Symbol plusMinusSide = new PlusMinus(new Root(getSide(sideToPlusMinus).getStoredSym(), rootBy.deepCopy()));
		plusMinusSide = plusMinusSide.standarizedForm();
		this.setSide(sideToPlusMinus, plusMinusSide);
	}
	
	public void rootSides(Symbol rootBy) {
		boolean isEven = new IsSymbolAlwaysEven().isAlwaysEven(rootBy);
		Symbol leftExponent = new GetSymbolExponent().getExponent(left.getStoredSym());
		Symbol rightExponent = new GetSymbolExponent().getExponent(right.getStoredSym());
		boolean leftExpoEquals = leftExponent.equals(rootBy);
		boolean rightExpoEquals = rightExponent.equals(rootBy);
		
		if (!isEven || (!leftExpoEquals && !rightExpoEquals)) {
			rootSide(Side.Left, rootBy);
			rootSide(Side.Right, rootBy);
		}
		else {
			///Clarify: here either leftExponent equals rootBy or rightExponent equals rootBy.
			///(see the previous if clause)
			if (leftExpoEquals) rootWithPlusMinus(Side.Right, rootBy);
			else rootWithPlusMinus(Side.Left, rootBy);
		}
	}
	
	///Returns the added symbol when isolating a symbol with a coefficient of removedCoefficient
	///Example: 5x = 3x, we want to isolate to left side -> removedCoefficient = 3, returns -3.
	private Symbol getAddedCoefficient(Symbol removedCoefficient) {
		Symbol addedCoefficient = new Multiplication(NumberSym.doubleSymbol(-1), removedCoefficient);
		Expression addedCoefficientExp = new Expression(addedCoefficient);
		addedCoefficientExp.simplify();
		return addedCoefficientExp.getStoredSym();
	}
	
	private Symbol getAddedSymbol(Symbol removedCoefficient, Symbol isolatedSymbol) {
		Symbol addedSymbolCoefficient = getAddedCoefficient(removedCoefficient);
		Symbol addedSymbol = new Multiplication(addedSymbolCoefficient, isolatedSymbol);
		Expression addedSymbolExp = new Expression (addedSymbol);
		addedSymbolExp.simplify();
		return addedSymbolExp.getStoredSym();
	}
	
	private void addToSides(Symbol toAdd) {
		left.setStoredSym(new Addition(left.getStoredSym(), toAdd.deepCopy()));
		left.standarize();
		right.setStoredSym(new Addition(right.getStoredSym(), toAdd.deepCopy()));
		right.standarize();
	}
	
	private void removeCoefficient(Symbol removedCoefficient, Symbol isolatedSym) {
		addToSides(getAddedSymbol(removedCoefficient, isolatedSym));
	}
	
	///Returns whether did action
	public boolean isolate(Symbol sym, Side moveToSide) {
		Symbol leftCoefficient = left.getStoredSym().getTotalPolynomCoefficients(sym);
		Symbol rightCoefficient = right.getStoredSym().getTotalPolynomCoefficients(sym);
		if (leftCoefficient.isZero() || rightCoefficient.isZero()) return false;
		
		else {
			Symbol removedCoefficient = moveToSide == Side.Left ? rightCoefficient : leftCoefficient;
			removeCoefficient(removedCoefficient, sym);
			return true;
		}
	}
	
	public boolean isolateVariableDegree(Symbol deg, Side moveToSide) {
		Symbol leftCoefficient = new ExplicitCoefficient(left.getStoredSym(), new Power(this.targetVariable(), deg)).getExplicitCoefficient();
		Symbol rightCoefficient = new ExplicitCoefficient(right.getStoredSym(), new Power(this.targetVariable(), deg)).getExplicitCoefficient();
		if ((moveToSide == Side.Left ? rightCoefficient : leftCoefficient).isZero()) return false;
		
		else {
			Symbol removedCoefficient = moveToSide == Side.Left ? rightCoefficient : leftCoefficient;
			removeCoefficient(removedCoefficient, new Power(this.targetVariable(), deg));
			return true;
		}
	}
	
	
	///TODO Implement method
	///Isolates symbol so the result symbol has a POSITIVE number coefficient.
	/*public void isolateToPositive(Symbol toIsolate) {
		Symbol leftCoefficient = left.getStoredSym().getTotalPolynomCoefficients(sym);
		Symbol rightCoefficient = right.getStoredSym().getTotalPolynomCoefficients(sym);
		if (leftCoefficient.isZero() || rightCoefficient.isZero()) return;
		
		else {
			Symbol removedCoefficient = moveToSide == Side.Left ? leftCoefficient : rightCoefficient;
			removeCoefficient(removedCoefficient, sym);
		}
	}*/
	
	private CalcNumber getAdditionFreeNumber(Symbol sym) {
		List<Symbol> params = sym.getParams();
		CalcNumber sum = new CalcNumber(0);
		for (Symbol nextP : params) sum = sum.add(this.getFreeNumber(nextP));
		return sum;
	}
	
	private CalcNumber getOtherFreeNumber(Symbol sym) {
		NumberInfo inf = sym.getIdentifier().isRawNumber();
		return inf.getIsNum() ? inf.getNum() : new CalcNumber(0);
	}
	
	///TODO move this method somewhere else....
	public CalcNumber getFreeNumber(Symbol sym) {
		if (sym.getType() == SymType.Addition) return getAdditionFreeNumber(sym);
		else return getOtherFreeNumber(sym);
	}
	
	
	///TODO Add support for isolating PARAMETER variables.
	///		(And MORE URGENT- for isolating IMPLICATED NUMBERS)
	public boolean isolateFreeNumber(Side toSide) {
		Expression removeFromSide = toSide == Side.Left ? getRight() : getLeft();
		CalcNumber toRemove = getFreeNumber(removeFromSide.getStoredSym());
		if (toRemove.equals(new CalcNumber(0))) return false;
		else {
			addToSides(new NumberSym(toRemove.minus()));
			return true;
		}
	}
	
	public void standarizeSides() {
		left.standarize();
		right.standarize();
	}
	
	public boolean simplify(Side side) {
		Expression toSimp = getSide(side);
		return toSimp.simplify();
	}
	
	public boolean simplifyNext(Side side) {
		Expression toSimp = getSide(side);
		return toSimp.simplifyNext();
	}
	
	public boolean lightSimplify(Side side) {
		Expression toSimp = getSide(side);
		return toSimp.lightSimplify();
	}
	
	public boolean isOpenForm(Side side) {
		return getSide(side).getStoredSym().isOpenForm();
	}
	
	private PolynomInfo isPolynomial(Expression expression) {
		return expression.getStoredSym().getIdentifier().isPolynom();
		
	}
	
	private boolean checkPolynomialEquation(PolynomInfo leftInf, PolynomInfo rightInf) {
		return leftInf.getIsPolynom() && rightInf.getIsPolynom();
	}
	
	private boolean plusMinusExists() {
		IsPlusMinusPresent check = new IsPlusMinusPresent();
		return check.containsPlusMinus(getLeft().getStoredSym()) != null || check.containsPlusMinus(getRight().getStoredSym()) != null;
	}
	
	private EquationSolveBehavior selectEquationType() {
		if (plusMinusExists()) return new PlusMinusEquationSolution(this);
		
		PolynomInfo leftPoly, rightPoly;
		leftPoly = isPolynomial(left);
		rightPoly = isPolynomial(right);
		if (checkPolynomialEquation(leftPoly, rightPoly)) return new PolynomEquationSolution(this);
		return new DefaultEquationSolveBehavior(this); ///TODO not good, change this.
	}
	
	private void validateTargetVariable() {
		
	}
	
	@Override
	public SolveableManipulateBehavior manipulateBehavior() {
		return manipulator;
	}
	
	public void setManipulateBehavior(SolveableManipulateBehavior newManipulator) {
		manipulator = newManipulator;
		//manipulator.getEquationManipulator().setEquation(this);
	}
	
	///Returns true if equation is of type "{targetVar} = {exp}" where {exp} DOES NOT contain {targetVar}.
	private boolean isEquationSolution(Expression varSide, Expression otherSide) {
		if (!varSide.getStoredSym().equals(this.targetVariable())) return false;
		
		if (!otherSide.getStoredSym().containsVariable(this.targetVariable())) return true;
		else return false;
	}
	
	///If equation is of type {num1}={num2}, returns 'every real number' if num1==num2 or 'no solution' otherwise.
	private EquationSolution tryGetDefiniteSolution() {
		boolean leftInf = getLeft().getStoredSym().isImplicatedNumber();
		boolean rightInf = getRight().getStoredSym().isImplicatedNumber();
		//if (!leftInf.getIsNum() || !rightInf.getIsNum()) return null;
		//else return leftInf.getNum().equals(rightInf.getNum()) ? new EveryRealNumberSolution(targetVariable()) :
		//															new NoSolution(targetVariable());
		if (!leftInf || !rightInf) return null;
		else return getLeft().getStoredSym().equals(getRight().getStoredSym()) 
				? new EveryRealNumberSolution(targetVariable()) :
				  new NoSolution(targetVariable()); 
		///TODO We need to simplify before doing this!
		///What if we equalize 4th root of 9 to 2nd root of 3?
		///equals will return false
														
	}
	
	@Override
	public String printSolveable(VariableIDTable table) {
		StringBuilder build = new StringBuilder();
		build.append(getLeft().getStoredSym().latexString(table));
		build.append(" = ");
		build.append(getRight().getStoredSym().latexString(table));
		return build.toString();
	}
	
	@Override
	public EquationSolution reachedSolution() {
		if (this.decidedSolution) return this.explicitSolution;
		if (plusMinusExists()) return null;
		
		if (isEquationSolution(getRight(), getLeft())) {
			this.manipulateBehavior().getEquationManipulator().getPolyManipulator().areSidesFullySimplified(new PolynomEquation(this));
			return new EquationSolution(targetVariable(), getLeft().getStoredSym());
		}
		if (isEquationSolution(getLeft(), getRight())) {
			this.manipulateBehavior().getEquationManipulator().getPolyManipulator().areSidesFullySimplified(new PolynomEquation(this));
			return new EquationSolution(targetVariable(), getRight().getStoredSym());
		}
		
		EquationSolution tryDefinite = tryGetDefiniteSolution();
		if (tryDefinite != null) return tryDefinite;
		
		return null;
	}
	
	@Override
	public Solveable stepSolve() {
		validateTargetVariable();
		EquationSolveBehavior advanceStep = selectEquationType();
		
		return advanceStep.nextStep();
	}
	
	///TODO Make Solveable an abstract class, and make fullSolve a public final method of Solveable class,
	///		as implementation is (almost) the same for all solveables.
	@Override
	public Solveable fullSolve() {
		validateTargetVariable(); ///TODO maybe just remove validateVariable thingy...
		try {
			return new FullSolve().fullSolve(this);
		}
		catch (NegativeRootException e) {
			this.explicitSolution = new NoRealSolution();
			this.decidedSolution = true;
			return this;
		}
	}
	
}
