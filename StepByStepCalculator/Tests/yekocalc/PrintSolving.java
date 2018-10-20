package yekocalc;

import latexpression.VariableIDDynamicTable;

public class PrintSolving extends PolynomEquationManipulateBehavior {
	StringBuilder output;
	VariableIDDynamicTable manager;
	public PrintSolving(StringBuilder output, VariableIDDynamicTable manager) {
		super();
		this.output = output;
		this.manager = manager;
	}
	
	//public PrintSolving(VariableIDDynamicTable manager) {
	//	this(null, manager);
	//}
	
	public void setOutput(StringBuilder newOut) {
		output = newOut;
	}
	
	private void println(String str) {
		output.append(str);
		output.append("\n");
		//System.out.println(str);
	}
	
	private void printEq(PolynomEquation pe) {
		Variable target = pe.getEquation().targetVariable();
		pe.getEquation().getLeft().getStoredSym().accept(new SortPolynomSymbol(target));
		pe.getEquation().getRight().getStoredSym().accept(new SortPolynomSymbol(target));
		
		this.println("$$ " + super.printEquation(pe, manager) + " $$");
	}
	
	@Override
	protected void onAreSidesFullySimplified(PolynomEquation pe) {
		//println("Simplify: ");
		printEq(pe);
	}

	@Override
	protected void onTrySimplifyNext(PolynomEquation pe) {
		//println("Simplify:");
		printEq(pe);
	}
	
	@Override
	protected void onOpenForm(PolynomEquation pe) {
		super.lightSimplifySides(pe);
		
		//println("Open parenthessis: ");
		printEq(pe);
	}
	
	@Override
	protected void onIsolateSingleDegree(PolynomEquation pe, Side toSide) {
		super.trySimplifySides(pe);
		
		//println("Isolate variable to the " + (toSide == Side.Left ? "left" : "right") + ":");
		printEq(pe);
	}
	
	@Override
	protected void onIsolateFreeNumber(PolynomEquation pe, Side toSide) {
		super.trySimplifySides(pe);
		
		//println("Isolate free number to the " + (toSide == Side.Left ? "left" : "right") + ":");
		printEq(pe);
	}
	
	@Override
	protected void onDivideVariableByCoefficient(PolynomEquation pe, Symbol dividedBy) {
		super.trySimplifySides(pe);
		
		//println("Divide sides by \\(" + dividedBy.simplifiedForm().latexString(manager) + "\\)");
		printEq(pe);
	}
	
	@Override
	protected void onRootingSides(PolynomEquation pe, int deg) {
		//println("Root both sides by " + Integer.toString(deg));
		printEq(pe);
	}
	
	@Override
	protected void onIsolateEverything(PolynomEquation pe, Side toSide) {
		super.trySimplifySides(pe);
		//println("Isolate to the " + (toSide == Side.Left ? "Left" : "Right") + ":");
		printEq(pe);
	}
	
	@Override
	protected void onQuadraticFunction(PolynomEquation pe, Symbol a, Symbol b, Symbol c) {
		//println("Use the quadratic function; \\(a=" + a.latexString(manager) + "\\,,\\,b="
		//											+ b.latexString(manager) + "\\,,\\,c="
		//											+ c.latexString(manager)+ "\\):");
		printEq(pe);
	}
	
}
