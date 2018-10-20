package yekocalc;

import latexpression.VariableIDDynamicTable;

public class TestingEnvironment {
	static final CalcNumber V_ID = new CalcNumber(1);
	static final CalcNumber B_ID = new CalcNumber(2);
	
	public static Variable v = new Variable(V_ID);
	public static Variable b = new Variable(B_ID);
	
	public static Symbol testSym = new Multiplication(new Addition(v,numSym(2)), new Addition(v, numSym(5)), new Addition(v, numSym(3)));
	//static Symbol testSym = new Addition(new Multiplication(v, numSym(3)), new Multiplication(v, numSym(6)), v);
	
	
	public static NumberSym numSym(double v) {
		return new NumberSym(dCalc(v));
	}
	
	public static CalcNumber dCalc(double v) {
		return new CalcNumber(v);
	}
	
	public static void printVarDegs(PolynomVarInfo vInf) {
		for (int i = 1; i <= vInf.getMaxDegree(); i++) {
			if (vInf.getDegreeExists(i)) System.out.print(i + " ");
		}
		System.out.println();
	}
	
	public static void printInfoDegs(PolynomInfo inf) {
		for (int i = 0; i < inf.varCount(); i++) {
			System.out.println("for variable " + inf.getInfo(i).getVariable().id().getDouble() + " :");
			printVarDegs(inf.getInfo(i));
		}
	}
	
	public static void printInfo(PolynomInfo inf) {
		System.out.println("count: " + inf.varCount());
		//System.out.println("degree of first variable: " + inf.getInfo(0).getMaxDegree());
		System.out.println("is even a polynom: " + inf.getIsPolynom());
		System.out.println("contains a free number: " + inf.getZeroDeg());
		printInfoDegs(inf);
	}
	
	public static void printSymbol(Symbol s) {
		System.out.println(s.latexString(new VariableIDDynamicTable()) + "\n");
	}
}
