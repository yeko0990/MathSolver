package yekocalc;
import latexpression.*;

public class LatexSolver {
	private StringBuilder output;
	private final VariableIDDynamicTable varManager = new VariableIDDynamicTable();
	
	private final PrintSolving polyManipulate = new PrintSolving(null, varManager); ///null because it is
																					///set in the constructor.
	private final PrintEquationSet eqSetManipulate = new PrintEquationSet(null, varManager);
	private SolveableManipulateBehavior printSteps;
	
	public LatexSolver() {
		output = new StringBuilder();
		printSteps = new SolveableManipulateBehavior(new EquationManipulateBehavior(polyManipulate),
													 eqSetManipulate);
		clearPrintOutput();
	}
	
	private void clearPrintOutput() {
		output = new StringBuilder();
		polyManipulate.setOutput(output);
		eqSetManipulate.setOutput(output);
		
	}
	
	private String solveExp(Expression exp) {
		return "Someday I would simplify expressions for you."; ///TODO implement this
	}
	
	private String cannotSolve() {
		output.append("\nOops, I dont know how to solve it...");
		return output.toString();
	}
	
	private String solveSolveable(Solveable sol, String onNoSolution) {
		output.append("Solving \\(" + sol.printSolveable(varManager) + "\\):");
		try {
			sol = sol.fullSolve();
		}
		catch (Exception e) { //Should catch only CannotSolve exceptions but I fear it would crash
							  //if there would be another error
			return cannotSolve(); 
		}
		Solution solution = sol.reachedSolution();
		if (solution != null) output.append("$$ $$ Solution:$$ $$ $$" + solution.printLatex(varManager) + "$$");
		else output.append(onNoSolution);
		
		output.append("$$ $$ $$ $$ $$ $$"); ///To fix some annoying bug in android MathView.
		return output.toString();
	}
	
	private String cantParseString() {
		return "I Cannot understand your expression";
	}
	
	
	///MAY RETURN NULL! If userStr cannot be parsed.
	private Solveable getSolveableFromUserStr(String userStr) {
		try {
			LatexUserString expressionString = new LatexUserString(userStr);
			Expression exp = new Expression(expressionString.toExpression().toSymbol(varManager));
			return new ExpressionSolveable(exp);
		} catch (IllegalLatexStringException e) {
			
		} catch (IllegalElementListException e) {
			
		}
		
		try {
			LatexEquationUserString equationString = new LatexEquationUserString(userStr);
			Equation eq = equationString.getEquation(varManager);
			eq.setManipulateBehavior(printSteps);
			eq.setSolvedVariable(new Variable(varManager.getID("x"))); ///TODO VERY TEMPORARY
			return eq;
		} catch (IllegalLatexStringException e) {
			
		} catch (IllegalElementListException e) {
			
		}
		return null;
	}
	
	public String userStringToLatex(String userStr) {
		Solveable parsedStr = this.getSolveableFromUserStr(userStr);
		if (parsedStr == null) return this.cantParseString();
		return "$$" + parsedStr.printSolveable(varManager) + "$$";
	}

	
	public String solveUserLatex(String userStr) {
		this.clearPrintOutput();
		Solveable solveable = this.getSolveableFromUserStr(userStr);
		
		return solveable != null ? this.solveSolveable(solveable, "Oops, I can't solve this...") : cantParseString();
	}

}
