package yekocalc;

import java.util.List;

///TODO move all logic out of this class, to PolynomEquation class.
public class PolynomEquationManipulateBehavior {
	
	public PolynomEquationManipulateBehavior(/*Equation eq*/) {
		//pe = new PolynomEquation(eq);
	}
	
	/*public PolynomEquationManipulateBehavior copy() {
		PolynomEquationManipulateBehavior newManipulate = new PolynomEquationManipulateBehavior();
		newManipulate.setEquation(pe != null ? pe.getEquation() : null);
		return newManipulate;
	}*/
	
	//public void setEquation(Equation eq) {
	//	pe = new PolynomEquation(eq);
	//}
	
	protected String printEquation(PolynomEquation pe, VariableIDTable idTable) {
		return pe.getEquation().printSolveable(idTable);
	}
	
	/*public boolean trySimplify(Side side) {
		return pe.getEquation().simplify(side);
	}*/
	
	protected void lightSimplifySide(PolynomEquation pe, Side side) {
		pe.getEquation().lightSimplify(side);
	}
	
	public void lightSimplifySides(PolynomEquation pe) {
		lightSimplifySide(pe, Side.Left);
		lightSimplifySide(pe, Side.Right);
	}
	
	protected boolean simplifySide(PolynomEquation pe, Side side) {
		return pe.getEquation().simplify(side);
	}
	
	protected boolean trySimplifySides(PolynomEquation pe) {
		boolean right = simplifySide(pe, Side.Right);
		boolean left = simplifySide(pe, Side.Left);
		return right || left;
	}
	
	protected void onAreSidesFullySimplified(PolynomEquation pe) {
		
	}
	
	public final boolean areSidesFullySimplified(PolynomEquation pe) {
		boolean ret =  !trySimplifySides(pe);
		if (!ret) onAreSidesFullySimplified(pe);
		return ret;
	}
	
	protected void onTrySimplifyNext(PolynomEquation pe) {
		
	}
	
	public final boolean trySimplifyNext(PolynomEquation pe, Side side) {
		boolean ret =  pe.getEquation().simplifyNext(side);
		if (ret) onTrySimplifyNext(pe);
		return ret;
	}
	
	//protected void onIsolate(Symbol toIsolate) {
	//	
	//}
	
	//public final void isolate(Symbol toIsolate) {
	//	pe.getEquation().isolate(toIsolate, Side.Left); ///TODO Why left...?
	//	onIsolate(toIsolate);
	//}
	
	public final boolean areSidesOpen(PolynomEquation pe) {
		return pe.getEquation().isOpenForm(Side.Left) && pe.getEquation().isOpenForm(Side.Right);
	}
	
	public final boolean areOpenForm(PolynomEquation pe) {
		return pe.getEquation().isOpenForm(Side.Left) && pe.getEquation().isOpenForm(Side.Right);
	}
	
	protected void onOpenForm(PolynomEquation pe) {
		
	}
	
	public final void openForm(PolynomEquation pe) {
		pe.getEquation().getLeft().toOpenForm();
		pe.getEquation().getRight().toOpenForm();
		onOpenForm(pe);
	}
	
	public final boolean isSingleDegreeEquation(PolynomEquation pe) {
		return pe.isSingleDegreeEquation();
	}
	
	public final boolean isFirstDegreeEquation(PolynomEquation pe ) {
		return pe.isMaxDegree(1);
	}
	
	public final boolean isSecondDegree(PolynomEquation pe) {
		return pe.isMaxDegree(2);
	}
	
	protected void onIsolateSingleDegree(PolynomEquation pe, Side toSide) {
		
	}
	
	///CALRIFICATION: This function is called when isolating a single degree equation. It essentially 
	///isolates ALL degrees.
	public final boolean tryIsolateSingleDegree(PolynomEquation pe, Side toSide) {
		boolean didAction =  pe.isolateAllDegrees(toSide);
		if (didAction) onIsolateSingleDegree(pe, toSide);
		return didAction;
	}
	
	protected void onIsolateFreeNumber(PolynomEquation pe, Side toSide) {
		
	}
	
	public final boolean tryIsolateFreeNumber(PolynomEquation pe, Side toSide) {
		boolean didAction = pe.isolateFreeNumber(toSide);
		if (didAction) onIsolateFreeNumber(pe, toSide);
		return didAction;
	}
	
	protected void onDivideVariableByCoefficient(PolynomEquation pe, Symbol dividedBy) {
		
	}
	
	///if coefficient is 0 or 1, returns null and DOES NOT divide.
	//private Symbol tryDivideByCoefficient(PolynomEquation pe, Expression divideFrom, Variable target) {
		/*Symbol coef = divideFrom.getStoredSym().getPolynomCoefficient(target);
		if (!coef.equals(NumberSym.zero)) {
			if (coef.equals(NumberSym.intSymbol(1)) || coef.containsVariable(target)) return null; ///If coefficient is one OR if it contains
																  ///the target variable, dont divide.
			pe.getEquation().divideSides(coef);
			onDivideVariableByCoefficient(pe, coef);
			return coef;
		}
		else return null;*/
	//}
	
	///Returns coefficient that was divided
	public final boolean tryDivideVariableByCoefficient(PolynomEquation pe) {
		/*Variable target = pe.getEquation().targetVariable();
		Expression left = pe.getEquation().getLeft();
		Expression right = pe.getEquation().getRight();
		
		Symbol coef = tryDivideByCoefficient(pe, left, target);
		if (coef != null) return true;
		
		coef = tryDivideByCoefficient(pe, right, target);
		if (coef != null) return true;
		return false;
		
		//else throw new RuntimeException(); ///TODO Create exception for this*/
		Expression dividedSymbol = new Expression(null);
		boolean divided = pe.divideBySingleDegreeCoefficient(dividedSymbol);
		if (divided) onDivideVariableByCoefficient(pe, dividedSymbol.getStoredSym());
		return divided;
	}
	
	protected void onRootingSides(PolynomEquation pe, int byDeg) {
		
	}
	
	///Rooting sides by the degree of the SINGLE variable
	public final void rootByVariableDegree(PolynomEquation pe) {
		int leftDeg = pe.tryGetSingleDegree(Side.Left);
		if (leftDeg != -1) {
			pe.getEquation().rootSides(NumberSym.intSymbol(leftDeg));
			onRootingSides(pe, leftDeg);
			return;
		}
		int rightDeg = pe.tryGetSingleDegree(Side.Right);
		if (rightDeg != -1) {
			pe.getEquation().rootSides(NumberSym.intSymbol(rightDeg));
			onRootingSides(pe, rightDeg);
			return;
		}
		else throw new RuntimeException(); ///TODO Create exception for this
	}
	
	protected void onIsolateEverything(PolynomEquation pe, Side toSide) {
		
	}

	public boolean tryIsolateEverything(PolynomEquation pe, Side toSide) {
		boolean degs = pe.isolateAllDegrees(toSide);
		boolean num = pe.isolateFreeNumber(toSide);
		if (degs || num) {
			onIsolateEverything(pe, toSide);
			return true;
		}
		else return false;
	}
	
	protected void onQuadraticFunction(PolynomEquation pe, Symbol a, Symbol b, Symbol c) {
		
	}
	
	public void applyQuadraticFunction(PolynomEquation pe) {
		List<Symbol> abc = pe.applyQuadraticFunction();
		onQuadraticFunction(pe, abc.get(0), abc.get(1), abc.get(2));
		
		this.lightSimplifySides(pe);
		while (this.trySimplifyNext(pe, Side.Right))
			this.lightSimplifySides(pe);
	}
}
