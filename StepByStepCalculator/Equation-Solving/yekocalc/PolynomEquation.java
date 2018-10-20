package yekocalc;

import java.util.*;

public class PolynomEquation {
	private Equation equation;
	private PolynomInfo leftInfo, rightInfo;
	//private boolean infoSet; ///If false, info objects need to be calculated.
	public PolynomEquation(Equation equationP) {
		equation = equationP;
	}
	
	private void setPolynomInfos(PolynomInfo leftInf, PolynomInfo rightInf) {
		leftInfo = leftInf;
		rightInfo = rightInf;
	}
	
	private PolynomInfo getPolyInfo(Expression e) {
		return e.getStoredSym().getIdentifier().isPolynom();
	}
	
	private void calculatePolynomInfos() {
		setPolynomInfos(getPolyInfo(equation.getLeft()), getPolyInfo(equation.getRight()) );
	}
	
	
	///TODO OPTIMIZATION *IMPORTANT*- implement in PolynomVarInfo class some method that returns the degress that
			///has been set at least once (the key set od the hash table). So if max degree is very big, and is the only
			///degree that exists, we dont have to iterate over all the degrees until we get to max degree.
	private boolean isSingleDegreeSet(boolean[] leftDegs, boolean[] rightDegs) {
		calculatePolynomInfos();
		int degreeFound = -1;
		boolean isDegreeFound = false;
		///TODO Wow, this is ugly
		for (int i = 0; i < leftDegs.length; i++) {
			if (leftDegs[i]) {
				if (isDegreeFound) {
					if (i == degreeFound) continue;
					else return false;
				}
				else {
					degreeFound = i;
					isDegreeFound = true;
				}
			}
		}
		for (int i = 0; i < rightDegs.length; i++) {
			if (rightDegs[i]) {
				if (isDegreeFound) {
					if (i == degreeFound) continue;
					else return false;
				}
				else {
					degreeFound = i;
					isDegreeFound = true;
				}
			}
		}
		return isDegreeFound;
	}
	
	public boolean isSingleDegreeEquation() {
		calculatePolynomInfos();
		
		Variable targetVar = equation.targetVariable();
		PolynomVarInfo leftVarInf = leftInfo.getInfo(targetVar);
		PolynomVarInfo rightVarInf = rightInfo.getInfo(targetVar);
		///TODO OPTIMIZATION *IMPORTANT*- implement in PolynomVarInfo class some method that returns the degress that
		///has been set at least once (the key set od the hash table). So if max degree is very big, and is the only
		///degree that exists, we dont have to iterate over all the degrees until we get to max degree.
		boolean ret =  isSingleDegreeSet(leftVarInf == null ? new boolean[0] : leftVarInf.getAllDegrees(),
								 rightVarInf == null ? new boolean[0] : rightVarInf.getAllDegrees());
		return ret;
	}
	
	public boolean isMaxDegree(int deg) {
		calculatePolynomInfos();

		Variable targetVar = equation.targetVariable();
		PolynomVarInfo leftVarInf = leftInfo.getInfo(targetVar);
		PolynomVarInfo rightVarInf = rightInfo.getInfo(targetVar);
		return leftVarInf.getMaxDegree() <= deg && rightVarInf.getMaxDegree() <= deg;
	}
	
	private boolean isolateDegree(int deg, Side toSide) {
		//Symbol degreeSymbol = new Power(equation.targetVariable(), NumberSym.doubleSymbol(deg));
		Symbol degreeSymbol = NumberSym.intSymbol(deg);
		return equation.isolateVariableDegree(degreeSymbol, toSide);
	}
	
	public boolean isolateAllDegrees(Side toSide) {
		calculatePolynomInfos();

		PolynomInfo toMove = toSide == Side.Left ? rightInfo : leftInfo;
		///TODO OPTIMIZATION *IMPORTANT*- implement in PolynomVarInfo class some method that returns the degress that
				///has been set at least once (the key set od the hash table). So if max degree is very big, and is the only
				///degree that exists, we dont have to iterate over all the degrees until we get to max degree.
		PolynomVarInfo targetVarInfo = toMove.getInfo(equation.targetVariable());
		boolean[] degs = targetVarInfo == null ? new boolean[0] : targetVarInfo.getAllDegrees();
		boolean didAction = false;
		for (int i = 0; i < degs.length; i++) {
			if (degs[i]) {
				if (isolateDegree(i, toSide)) didAction = true;
			}
		}
		return didAction;
	}
	
	public boolean isolateFreeNumber(Side toSide) {
		return equation.isolateFreeNumber(toSide);
	}
	
	///Returns the degree IF single degree with no free num, returns -1 otherwise.
	private int checkSingleDegreeNoFreeNum(PolynomInfo inf, Variable target) {
		PolynomVarInfo targetInfo = inf.getInfo(target); 
		if (targetInfo == null) return -1;
		List<Integer> degs = targetInfo.matchingDegs(true);
		if (degs.size() != 1) return -1;
		return degs.get(0);
	}
	
	///Checks whether the passed side is a SINGLE degree, WITHOUT FREE NUMBER, and returns THE DEGREE.
	///returns -1 if the side is not single degree\contains free number.
	public int tryGetSingleDegree(Side toCheck) {
		calculatePolynomInfos();

		Variable targetVar = equation.targetVariable();
		PolynomInfo sideInf = toCheck == Side.Left ? leftInfo : rightInfo;
		int sideDeg = checkSingleDegreeNoFreeNum(sideInf, targetVar);
		return sideDeg;
	}
	
	private boolean divideByDegreeCoefficient(Symbol side, int degree, Expression retSymbolDivided) {
		Symbol coefficient = new ExplicitCoefficient(side, new Power(equation.targetVariable(), NumberSym.intSymbol(degree))).getExplicitCoefficient();
		if (coefficient.equals(NumberSym.intSymbol(1)) || coefficient.equals(NumberSym.zero)) return false;
		
		retSymbolDivided.setStoredSym(coefficient);
		equation.divideSides(coefficient);
		return true;
	}
	
	///When the equation is at the state of (coefficient)*x^(something) = (something without x),
	///divides the equations sides by (coefficient).
	///Puts the divided symbol in retSymbolDivided (to let the caller know what it was)
	///Returns true if did something.
	public boolean divideBySingleDegreeCoefficient(Expression retSymbolDivided) {
		this.calculatePolynomInfos();
		int leftDegree = this.tryGetSingleDegree(Side.Left);
		if (leftDegree != -1 && !equation.getRight().getStoredSym().containsVariable(equation.targetVariable())) {
			return divideByDegreeCoefficient(equation.getLeft().getStoredSym(), leftDegree, retSymbolDivided);
		}
		
		int rightDegree = this.tryGetSingleDegree(Side.Right);
		if (leftDegree != -1 && !equation.getLeft().getStoredSym().containsVariable(equation.targetVariable())) {
			return divideByDegreeCoefficient(equation.getRight().getStoredSym(), rightDegree, retSymbolDivided);
		}
		return false; ///TODO Maybe throw an exception? If we fail here we might be stuck in an inifinte loop of a solution.
	}
	
	private void quadraticTransform(Symbol a, Symbol b, Symbol c) {
		Symbol left = this.equation.targetVariable();
		
		NumberSym minusOne = NumberSym.intSymbol(-1);
		NumberSym two = NumberSym.intSymbol(2);
		NumberSym four = NumberSym.intSymbol(4);
		Symbol rightNumerator = new Addition(
											new Multiplication(NumberSym.intSymbol(-1), b.deepCopy()),
											 new PlusMinus(new Root(new Addition(new Power(b.deepCopy(), two),
													 							 new Multiplication(minusOne, four, a.deepCopy(), c.deepCopy()))
													 				, two)
													 	   )
											 );
		Symbol right = new Fraction(rightNumerator, new Multiplication(two, a.deepCopy()));
		
		this.equation.setLeft(left);
		this.equation.setRight(right);
	}
	
	///Returns a,b,c in indices 0,1,2 respectively.
	public List<Symbol> applyQuadraticFunction() {
		Expression fullSide; //The side with the variables and the free number.
		if (this.equation.getLeft().getStoredSym().equals(NumberSym.zero)) fullSide = this.equation.getRight();
		else if (this.equation.getRight().getStoredSym().equals(NumberSym.zero)) fullSide = this.equation.getLeft();
		else throw new RuntimeException();
	
		Variable variable = this.equation.targetVariable();
		Symbol getFromSide = fullSide.getStoredSym();
		Symbol a = new ExplicitCoefficient(getFromSide, new Power(this.equation.targetVariable(), NumberSym.intSymbol(2))).getExplicitCoefficient();
		Symbol b = new ExplicitCoefficient(getFromSide, this.equation.targetVariable()).getExplicitCoefficient();;
		Symbol c = new NumberSym(this.equation.getFreeNumber(getFromSide));
		
		this.quadraticTransform(a, b, c);
		
		List<Symbol> ret = new ArrayList<Symbol>();
		ret.add(a); ret.add(b); ret.add(c);
		
		return ret;
	}
	
	public Equation getEquation() {
		return equation;
	}
	
}
