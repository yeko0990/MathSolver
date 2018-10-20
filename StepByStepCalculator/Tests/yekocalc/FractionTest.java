package yekocalc;

public class FractionTest extends TestingEnvironment {

	static Symbol testSym = new Multiplication(numSym(3), new Fraction(v, numSym(10)));
	
	public static void main(String[] args) {
		//printSymbol(testSym.simplifiedForm(MutableBoolean.NULL));
		Symbol s = (new NumberSym(testSym.getIdentifier().isTranslatedNumber().getNum()));
		printSymbol(s);
	}

}