package yekocalc;
import java.util.*;

///a symbol which is dissolved into potentially-coefficient parts.
///5ax^2 -> 5 , a:1 , x:2,	 y^3*x^y -> y:3, x:y
public class DissolvedSymbol implements DeepCopiable<DissolvedSymbol>{
	private MutableKeyHashMap<Symbol, Symbol> dissolvedSyms;
	
	public DissolvedSymbol() {
		dissolvedSyms = new MutableKeyHashMap<Symbol, Symbol>();
	}
	
	@Override
	public DissolvedSymbol deepCopy() {
		DissolvedSymbol buff = new DissolvedSymbol();
		Set<Symbol> bases = this.dissolvedSymbols();
		for (Symbol nextBase : bases) {
			buff.setDissolvedExponent(nextBase, this.getDissolvedExponent(nextBase).deepCopy());
		}
		return buff;
	}
	
	public Symbol getDissolvedExponent(Symbol baseSym) {
		
		if (!dissolvedSyms.containsKey(baseSym)) {
			///Just for math integrity- 0^0 is undefined so we can't return 0 when baseSym is 0
			//if (baseSym.equals(NumberSym.zero)) throw new IllegalArgumentException();
			return NumberSym.zero;
		}
		else return dissolvedSyms.get(baseSym);
	}
	
	public void setDissolvedExponent(Symbol baseSym, Symbol exp) {
		if (baseSym == null || exp == null) throw new IllegalArgumentException();
		dissolvedSyms.put(baseSym, exp);
	}
	
	private Symbol simplifySymbol(Symbol sym) {
		Expression exp = new Expression(sym);
		exp.simplify();
		return exp.getStoredSym();
	}
	
	///Used when adding a symbol to exponent, multiplying exponent by symbol, etc...
	private void manipulateExponent(Symbol baseSym, Symbol newExp) {
		Symbol finalExp = simplifySymbol(newExp);
		setDissolvedExponent(baseSym, finalExp);
	}
	
	///Adds exp to the exponent, for example adding 2 to x:
	///x:2 -> x:4
	public void addToDissolvedSymbolExpo(Symbol baseSym, Symbol toAdd) {
		if (baseSym == null || toAdd == null) throw new IllegalArgumentException();
		manipulateExponent(baseSym, new Addition(getDissolvedExponent(baseSym), toAdd));
	}
	
	public void multiplyDissolvedSymbolExpo(Symbol baseSym, Symbol toMultiply) {
		if (baseSym == null || toMultiply == null) throw new IllegalArgumentException();
		manipulateExponent(baseSym, new Multiplication(getDissolvedExponent(baseSym), toMultiply));
	}
	
	///Takes all of the bases' exponents in both dissolvedSymbols and adds them
	public void mixDissolvedSymbol(DissolvedSymbol toMix) {
		Set<Symbol> otherBases = toMix.dissolvedSymbols();
		for (Symbol nextBase : otherBases) {
			this.addToDissolvedSymbolExpo(nextBase, toMix.getDissolvedExponent(nextBase));
		}
	}
	
	public Set<Symbol> dissolvedSymbols() {
		return dissolvedSyms.keySet();
	}
	
	//Removes from the dissolvedSymbols set all of the keys who's exponents are 0.
	private void removeZeroExponents() {
		Set<Symbol> bases = dissolvedSymbols();
		Iterator<Symbol> iterate = bases.iterator();
		Symbol nextBase, nextExpo;
		while (iterate.hasNext()) {
			nextBase = iterate.next();
			nextExpo = this.getDissolvedExponent(nextBase);
			if (nextExpo.equals(NumberSym.zero)) iterate.remove();
		}
	}
	
	///Reduction of an exponent E1 by another exponent E2 is simply subtraction by the other exponent: E1 = E1-E2
	private void reduceExponent(Symbol baseSym, Symbol reduceFromExponent) {
		Symbol addToExponent = new Multiplication(NumberSym.doubleSymbol(-1), reduceFromExponent); //just -1 * reduceFromExponent
		addToDissolvedSymbolExpo(baseSym, addToExponent);
	}
	
	public DissolvedSymbol getCoefficient(DissolvedSymbol toExtract) {
		DissolvedSymbol result = this.deepCopy();
		Set<Symbol> extractedBases = toExtract.dissolvedSymbols();
		for (Symbol nextBase : extractedBases) {
			result.reduceExponent(nextBase, toExtract.getDissolvedExponent(nextBase));
		}
		removeZeroExponents();
		return result;
	}
	
	/*private boolean isNegativeExponent(Symbol expo) {
		NumberInfo inf = expo.getIdentifier().isRawNumber();
		if (!inf.getIsNum()) return false; ///TODO ADD CODE FOR: if it is not a number, check whether its a multiplication
										   ///in -1, for example: -x, -y^2... Because such exponents, EVEN IF X<0,
										   ///should be considered negative in the contex of algebric extraction result.
										   ///{ clarification: extract a^(2x) from a^x : result is a^(-x)) }
		else return inf.getNum().compareTo(new CalcNumber(0)) < 0;
	}*/
	
	public boolean hasNegativeExponent(Symbol baseSym) {
		Symbol expo = getDissolvedExponent(baseSym);
		return new SymbolLooksNegative().looksNegative(expo);
	}
	
	public boolean containsNegativePowers() {
		Set<Symbol> bases = dissolvedSymbols();
		for (Symbol b : bases) {
			if (hasNegativeExponent(b)) return false;
		}
		return true;
	}
	
	private Symbol baseToSymbol(Symbol baseSym, Symbol exponent) {
		return new Power(baseSym, exponent);
	}
	
	public Symbol toPowers() {
		Symbol res = new Multiplication();
		List<Symbol> resParams = new LinkedList<Symbol>();
		Set<Symbol> bases = this.dissolvedSymbols();
		Symbol nextExpo;
		for (Symbol nextBase : bases) {
			nextExpo = this.getDissolvedExponent(nextBase);
			if (!nextExpo.equals(NumberSym.zero)) resParams.add(baseToSymbol(nextBase, nextExpo));
		}
		if (resParams.size() == 0) res = NumberSym.doubleSymbol(1);
		res.setParams(resParams);
		return res.standarizedForm(); ///TODO OPTIMIZATION- Maybe instead of standarizing, just manually transform 
									  ///					powers with exponent of 1 to the base (x^1 -> x). 
	}
	
	private void sortBasesByExponentPositivity(List<Symbol> positives, List<Symbol> negatives) {
		Set<Symbol> bases = this.dissolvedSymbols();
		SymbolLooksNegative looksNegative = new SymbolLooksNegative();
		for (Symbol next : bases) {
			if (looksNegative.looksNegative(this.getDissolvedExponent(next))) negatives.add(next);
			else positives.add(next);
		}
	}
	
	private Symbol getNumerator(List<Symbol> numeratorBases) {
		Multiplication multi = new Multiplication();
		List<Symbol> params = multi.getParams();
		for (Symbol next : numeratorBases) {
			params.add(new Power(next, this.getDissolvedExponent(next)));
		}
		return multi.standarizedForm();
	}
	
	private Symbol getDenominator(List<Symbol> denominatorBases) {
		if (denominatorBases.size() == 0) return NumberSym.intSymbol(1);
		
		Multiplication multi = new Multiplication();
		List<Symbol> params = multi.getParams();
		Symbol nextExponent;
		for (Symbol next : denominatorBases) {
			nextExponent = new Multiplication(NumberSym.intSymbol(-1), this.getDissolvedExponent(next)).simplifiedForm();
			params.add(new Power(next, nextExponent));
		}
		
		///TODO Perhaps standarizing here is too costly, and we can just cover the case when params.size is 1
		///		with an if clause.
		return multi.standarizedForm();
	}
	
	///MOTIVATION: (created because of FractionCancelation simplification)
	///Standarization may transform fraction to another symbol (2x/1 -> 2x).
	///So in order to always return a fraction we need to skip standarizing.
	public Fraction toUnstandarizedFraction() {
		List<Symbol> numeratorBases = new LinkedList<Symbol>();
		List<Symbol> denominatorBases = new LinkedList<Symbol>();
		
		sortBasesByExponentPositivity(numeratorBases, denominatorBases);
		
		
		Fraction frac = new Fraction(getNumerator(numeratorBases), getDenominator(denominatorBases));
		return frac;
	}
	
	public Symbol toFraction() {
		return this.toUnstandarizedFraction().standarizedForm();
	}
}
