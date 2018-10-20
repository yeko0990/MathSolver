package yekocalc;

import latexpression.LatexUserString;

public class EquationOrSetTest {
	static VariableIDDynamicTable manager = new VariableIDDynamicTable();
	static Variable v = new Variable(new CalcNumber(1));
	
	static Symbol left = new LatexUserString("x^2 + 45(x+3) - 45x").toExpression().toSymbol(manager);
	static Symbol right = new LatexUserString("136").toExpression().toSymbol(manager);
	
	
	static NumberSym numSym(int num) {
		return NumberSym.intSymbol(num);
	}
	
	public static void main(String[] args) {
		long t1 = System.nanoTime();
		//manager.getID("x");
		StringBuilder build = new StringBuilder();
		PrintSolving printSolving = new PrintSolving(build, manager);
		
		//Symbol coef = right.getTotalPolynomCoefficients(v);
		//Symbol coef = new PolynomDegreeCoefficient(left, v).getDegreeCoefficient(NumberSym.intSymbol(2));
		//System.out.println(coef.latexString(manager));
		
		Expression leftE, rightE;
		leftE = new Expression(left);
		rightE = new Expression(right);
		
		Equation eq = new Equation(leftE, rightE, v, new SolveableManipulateBehavior(new EquationManipulateBehavior(printSolving), 
																					 new PrintEquationSet(build, manager)));
		
		System.out.println("Solving \\(" + eq.printSolveable(manager) + "\\): $$ $$");
		Solveable finalS = eq.fullSolve();
		Solution s = finalS.reachedSolution();
		long t2 = System.nanoTime();
		//System.out.println((t2-t1)/1000000000.0);
		System.out.println(build.toString());
		System.out.println("$$" + s.printLatex(manager) + "$$");
	}

}
