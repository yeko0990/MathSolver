package yekocalc;

public class PowerSymbolTest extends TestingEnvironment {

	static Symbol test = new Multiplication(new Addition(v, numSym(1)), v, numSym(2));
	
	public static void main(String[] args) {
		long t1 = System.nanoTime();
		Symbol t = test;
		printSymbol(test.getCoefficient(v));
		for (int i = 0; i < 100000; i++) test.getCoefficient(v);
		long t2 = System.nanoTime();
		System.out.println("seconds elapsed: " + (double)(t2-t1) / 1000000000.0);
	}

}
