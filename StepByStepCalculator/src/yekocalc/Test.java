package yekocalc;
import java.io.*;


///TODO:
///-EFFICIENCY PROBLEMS:
///		*FIXED* In a standarization of a method, each repetition of the standarization leads to the creation of
///		 a full deep copy of the symbol. Big overhead...

public class Test {
	static final CalcNumber V_ID = new CalcNumber(1);
	static final CalcNumber B_ID = new CalcNumber(2);

	static Variable v = new Variable(V_ID);
	static Variable b = new Variable(B_ID);
	//static Symbol testSym = new Addition(new Multiplication(new Pow(v, new DoubleCalcNumber(2)), new DoubleCalcNumber(4)), new DoubleCalcNumber(-453));
	//static Symbol testSym = new Addition(numSym(2), new Addition(v), new Addition(b, numSym(3), numSym(5)));
	//static Symbol testSym = new Multiplication(new Addition(v,numSym(-7)), new Addition(numSym(6), numSym(23)), numSym(3), 
	//											new Multiplication(numSym(2), numSym(1)));
	//static Symbol testSym = new Addition(new Addition(numSym(2), numSym(-10), v, numSym(2)), numSym(2));
	static Symbol testSym = new Multiplication(new Addition(v, numSym(4)), new Addition(v, numSym(1)));
	static NumberSym numSym(double v) {
		return new NumberSym(dCalc(v));
	}
	
	static CalcNumber dCalc(double v) {
		return new CalcNumber(v);
	}
	
	static void printVarDegs(PolynomVarInfo vInf) {
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
		return;
	}
	
	public static void main(String[] args) {
		//try {
		Expression testExp = new Expression(testSym);
		System.out.println("d");
		PolynomInfo inf = testSym.getIdentifier().isPolynom();
		//testSym.symStandarizer().standarize();
		testExp.standarize();
		testExp.simplify();
		printSymbol(testSym);
		printInfo(inf);
		//}
		//catch (NullPointerException npe) {
		//	npe.;
		//}
	}

}
