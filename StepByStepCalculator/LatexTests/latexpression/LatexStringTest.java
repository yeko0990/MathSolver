package latexpression;

import yekocalc.CalcNumber;
import yekocalc.Equation;
import yekocalc.EquationManipulateBehavior;
import yekocalc.Expression;
import yekocalc.MutableBoolean;
import yekocalc.PrintSolving;
import yekocalc.SolveableManipulateBehavior;
import yekocalc.Symbol;
import yekocalc.Variable;
import yekocalc.VariableIDDynamicTable;

public class LatexStringTest {
	static String str = "2x/2";
	static String str2 = "0";
	//static String str = "6-5";
	//static String str = "6*(-5)";
	
	static Expression exp(Symbol s) {
		return new Expression(s);
	}
	
	public static void main(String[] args) {
		//CalcNumber four = new CalcNumber(1);
		//System.out.println(four.root(new CalcNumber(3)).toString());
		VariableIDDynamicTable manager = new VariableIDDynamicTable();
		LatexUserString left = new LatexUserString(str);
		LatexUserString right = new LatexUserString(str2);
		PrintSolving printSolving = new PrintSolving(null, manager);
		
		Symbol simpLeft = left.toExpression().toSymbol(manager);
		System.out.println(simpLeft.simplifiedForm().latexString(manager));
		
		StringBuilder output = new StringBuilder();
		printSolving.setOutput(output);
		
		System.out.println(left.toExpression().toSymbol(manager).simplifiedForm().latexString(manager));
		Equation eq = new Equation(exp(left.toExpression().toSymbol(manager)), exp(right.toExpression().toSymbol(manager)),
									new Variable(manager.getID("x")),
									new SolveableManipulateBehavior(new EquationManipulateBehavior(printSolving)));
		eq.fullSolve();
		System.out.println(output.toString());
		System.out.println("Solution: $$" + eq.reachedSolution().printLatex(manager) + "$$");
	}
}
