package yekocalc;
import java.util.*;
import latexpression.*;

import java.text.DecimalFormat;

///See explanation in class Symbol
abstract class SymbolStandarizer {
	protected Symbol sym;
	
	///This method should write to tookAction whether an action was taken, and return standarized symbol
	abstract public Symbol internalStandarize(MutableBoolean tookAction);
	
	public SymbolStandarizer() {
		
	}
	
	///Standarize each parameter
	private void standarizeParameters() {
		List<Symbol> params = sym.getParams();
		if (params == null) return;
		for (int i = 0; i < params.size(); i++)
			params.set(i, params.get(i).standarizedForm());
	}
	
	///Generally only standarizedForm(Symbol s) and StandarizerSet class use this function.
	public void updateContex(Symbol s) {
		sym = s;
	}
	
	final public Symbol standarizedForm(Symbol s) {
		updateContex(s);
		MutableBoolean isChanged = new MutableBoolean(false);
		/*do {
			standarizeParameters();
			sym = internalStandarize(isChanged);
		} while(isChanged.value());
		*/
		standarizeParameters();
		sym = internalStandarize(isChanged);
		if (isChanged.value()) sym = sym.standarizedForm();
		return sym;
	}
}

//A standarizer which does nothing
class NoStandarize extends SymbolStandarizer {
	public NoStandarize() {
		super();
	}
	@Override
	public Symbol internalStandarize(MutableBoolean tookAction) {
		tookAction.set(false);
		return sym;
	}
}

///"Opens up" associative functions: add(4, add(10, x)) -> add(4,10,x)
class AssociativeOpener {
	private Symbol func;
	public AssociativeOpener(Symbol toOpen) {
		func = toOpen;
	}
	
	///Removes parameter of func at index i, then adds to func parameters list the parameters of the removed parameter.
	protected void openParam(List<Symbol> params, int index) {
		Symbol opened = params.get(index);
		List<Symbol> addedSymbols = opened.getParams();
		params.remove(index);
		for (int i = 0; i < addedSymbols.size(); i++) {
			params.add(addedSymbols.get(i));
		}
	}
	
	///Returns whether an action was taken
	public boolean open() {
		boolean action = false;
		SymType type = func.getType();
		List<Symbol> params = func.getParams();
		for (int i = 0; i < params.size(); i++) {
			if (type.equals(params.get(i).getType())) {
				action = true;
				openParam(params, i);
				i--; //i--: So if somehow no symbol is added to parameters and 1 symbol is removed,
					 //we wont miss a parameter in the 'for' loop.
			}
		}
		return action;
	}
}

class AssociativeStandarizer extends SymbolStandarizer {
	public AssociativeStandarizer() {
		super();
	}
	
	protected boolean openAssociativeFunctions() {
		AssociativeOpener opener = new AssociativeOpener(sym);
		return opener.open();
	}
	@Override
	public Symbol internalStandarize(MutableBoolean tookAction) {
		if (sym.getParams() == null) return sym;
		tookAction.set(openAssociativeFunctions());
		return sym;
	}
}


/*abstract class CalcNumber implements Comparable<CalcNumber> {
	//protected abstract void init(int n);
	//protected abstract void init(double n);
	
	@Override
	public String toString() {
		return valueToString();
	}
	
	protected abstract String valueToString();
	
	public abstract CalcNumber add(CalcNumber v);
	public abstract CalcNumber sub(CalcNumber v);
	public abstract CalcNumber mul(CalcNumber v);
	public abstract CalcNumber div(CalcNumber v);
	public abstract CalcNumber pow(CalcNumber v);
	public abstract CalcNumber minus();
	
	public abstract double getDouble();
	
	//public abstract void set(int num);
	//public abstract void set(double num);
	
	public abstract CalcNumber copy();
	
	protected abstract boolean internalEquals(Object obj);
	@Override
	public final boolean equals(Object obj) {
		return internalEquals(obj);
	}
	
	protected abstract int internalHashCode();
	@Override
	public final int hashCode() {
		return internalHashCode();
	}
}*/

///A simple implementation of CalcNumber with a double value
/*class DoubleCalcNumber extends CalcNumber {
	///Field is final so a CalcNumber instance would represent a specific value, and could be used in many places.
	private final double val;
	public DoubleCalcNumber(int num) {
		val = (double)num;
	}
	public DoubleCalcNumber(double num) {
		val = num;
	}
	
	@Override
	protected String valueToString() {
		DecimalFormat formatter = new DecimalFormat("#.#######");
		return formatter.format(val);
		
	}

	@Override
	public double getDouble() {
		return val;
	}
	//@Override
	//public void set(int num) {
	//	val = (double)num;
	//}
	//@Override
	//public void set(double num) {
	//	val = num;
	//}
	
	@Override
	public CalcNumber add(CalcNumber v) {
		return new DoubleCalcNumber(val + v.getDouble());
	}
	@Override
	public CalcNumber sub(CalcNumber v) {
		return new DoubleCalcNumber(val - v.getDouble());
	}
	@Override
	public CalcNumber mul(CalcNumber v) {
		return new DoubleCalcNumber(val * v.getDouble());
	}
	@Override
	public CalcNumber div(CalcNumber v) {
		return new DoubleCalcNumber(val / v.getDouble());
	}
	@Override
	public CalcNumber pow(CalcNumber v) {
		///For now, lets just implement a power by non-negative int
		///(NEED TO BE CHANGED EVENTUALLY)
		double originalVal = 1;
		for (int i = 0; i < v.getInt(); i++) originalVal*=val;
		return new DoubleCalcNumber(originalVal);
	}
	@Override
	public CalcNumber minus() {
		return mul(new DoubleCalcNumber(-1));
	}
	@Override
	public CalcNumber copy() {
		return new DoubleCalcNumber(val);
	}
	
	@Override
	protected int internalHashCode() {
		return Objects.hashCode(val);
	}
	
	@Override
	protected boolean internalEquals(Object otherNum) {
		if (otherNum instanceof CalcNumber) {
			CalcNumber otherDoub = (CalcNumber) otherNum;
			return otherDoub.compareTo(this) == 0;
		}
		else return false;
	}
	
	public int compareTo(CalcNumber c) {
		double ret = val - c.getDouble();
		if (ret < 0) return -1;
		if (ret > 0) return 1;
		else return 0;
	}
	
	
}*/

enum SymType {
	None, Number, Variable, Addition, Multiplication, Fraction, Power, Root, PlusMinus
}

///TODO:
///-Validate deepCopy() method

///HOW TO PROPERLY INHERIT FROM SYMBOL:
///- Implement the abstract methods.
///- Override createOpenForm() IF the symbol may be opened up (see definition of 'open form' in the class OpenForm)
///- Override 'equals', 'getCoefficient', 'getEffectiveCoefficient' methods if behavior is different than the default.
///- Override 'toDissolvedSymbol' if behaviour is different than original
///- Override 'copyShallowSymbol' method if behavior is different than the default. (if a deep copy
///  creation includes actions other than deep copying the parameters and SHALLOW copying the id).
public abstract class Symbol implements DeepCopiable<Symbol> {
	//protected SymType type = SymType.None;
	protected Parameters parameters; ///TODO explain about parameters
	protected OpenForm openForm; ///TODO explain here what is this
	//protected List<Symbol> parameters; ///The symbols parameters - usually the function parameters
									   ///(if the symbol is a function)
	
	protected abstract Symbol copySymbolType(); 	///*USED IN DEEP COPY*
													///Returns a symbol of the same type as the symbol who was copied.
	public abstract void accept(SymbolVisitor visitor);
	public final String latexString(VariableIDTable idList) {
		return this.latex(idList).toLatexStr();
	}
	
	public abstract LatexExpression latex(VariableIDTable idList);
	
	protected SymbolID symIdType; ///Symbols may or may not have an id.
								  //Id can be used differently for each type.
	  							  //For variables, id can be used to identify the variable (x id may be 4, y id may be 14...)
	  							  //For numbers, id can be used to return the value of the number.
	  							  //Generally for functions, id has no meaning.
	
	protected SymbolStandarizer symStd;	///Standarized form is used to enforce a uniform structuring of symbols.
	 									///At the time of writing, standarized form of a symbol is:
	 									///1. a form where no associative function has a parameter of its type. Example:
										///Non-standarized: add(4, add(3,2))
		 								///Standarized: add(4, 3, 2)
	
										///2. a form where no multiple-parameters function that have only 1 parameter
										///exists. Example:
										///add(3) -> 3, mul(x) -> x
	
	protected FullSimplifier symSimp;///Simplifier may be able to transform the current symbol into an alternative form of
	  								   ///the same meaning, for example: add(4, 6) --> 10
	  								   ///					     		 add(4,x,mul(2,x)) --> add(4, mul(3,x))
	  								   ///						 		 add(x, mul(y, x) --> mul(x, add(1, y))
	
	protected SymbolPatternIdentifier symId;///NOTES ABOUT IDENTIFIERS
											///A pattern may be a polynom, a number, a complex number etc.
											///Identifiers are able to identify whether the symbols are a raw {pattern}
											///									example: add(5, x, pow(x, 2)) is a polynom pattern
											///								and whether the symbols may be translated to a {pattern} 
											///									example: mul(pow(2,x), div(6, pow(2,x)))
											///									translates to mul(6) -> 6 which is a number
	
	///protected SymbolProperties symProps;//Contains information such as whether symbol is associative, whether ordering of parameters
										//is of meaning, whether the number of parameters is restricted to a certain range, etc.
	
	protected abstract SymbolID createIdType();
	protected abstract SymbolStandarizer createStd();
	protected abstract FullSimplifier createSimp();
	protected abstract SymbolPatternIdentifier createId();
	
	protected abstract Parameters createParameters();
	protected OpenForm createOpenForm() {
		return new NoActionOpenForm();
	}
	///protected abstract SymbolProperties createProps();
	
	protected void initSymbolSpecifics()
	{
		symIdType = createIdType();
		symStd = createStd();
		symSimp = createSimp();
		symId = createId();
		parameters = createParameters();
		openForm = createOpenForm();
		///symProps = createProps();
	}
	
	public Symbol() {
		initSymbolSpecifics();
	}
	
	public Symbol(List<Symbol> paramsP) {
		this();
		parameters.setParameters(paramsP);
	}
	
	public Symbol(Symbol...paramsP) {
		this(new LinkedList<Symbol>(Arrays.asList(paramsP)));
	}
	
	public CalcNumber id() {
		return symIdType.Id();
	}
	
	public void setId(CalcNumber newId) {
		symIdType.setId(newId);
	}
	
	public Symbol standarizedForm() {
		return symStd.standarizedForm(this);
	}
	
	public SymbolStandarizer symStandarizer() {
		return symStd;
	}
	
	///public FullSimplifier symSimplifier() {
	///	return symSimp;
	///}
	
	public SymbolPatternIdentifier symDeterminator() {
		return symId;
	}
	
	/*
	 public boolean simplify() {
		return symSimplifier().simplify();
	}
	
	public boolean simplifyNext() {
		return symSimplifier().simplifyNext();
	}
	*/
	
	///public SymbolProperties props() {
	///	return symProps;
	///}
	
	public abstract SymType getType();
	
	///TODO consider delete setParams and getParams, not every symbol is parameterized...
	public void setParams(List<Symbol> newParams) {
		parameters.setParameters(newParams);
	}
	
	public List<Symbol> getParams() {
		return parameters.getParameters();
	}
	
	public SymbolPatternIdentifier getIdentifier() {
		return symId;
	}
	
	public Symbol simplifiedForm(MutableBoolean didAction) {
		return symSimp.simplify(didAction, false);
	}
	
	public Symbol simplifiedForm() {
		return this.simplifiedForm(MutableBoolean.NULL);
	}
	
	public Symbol lightSimplifiedForm(MutableBoolean didAction) {
		return symSimp.simplify(didAction, true);
	}
	
	public Symbol nextSimplifyForm(MutableBoolean didAction) {
		return symSimp.simplifyNext(didAction, false);
	}
	
	public Symbol nextLightSimplifyForm(MutableBoolean didAction) {
		return symSimp.simplifyNext(didAction, true);
	}
	
	//TODO Why is it here? maybe delete...
	public Multiplication toMultiply() { ///Only overriden by Multiply class to return itself
		return new Multiplication(this);
	}
	
	public DissolvedSymbol toDissolvedSymbol() {
		DissolvedSymbol ret = new DissolvedSymbol();
		ret.addToDissolvedSymbolExpo(this, NumberSym.doubleSymbol(1));
		return ret;
	}
	
	public final Symbol getCoefficient(Symbol toExtract) {
		DissolvedSymbol coefficient = this.toDissolvedSymbol().getCoefficient(toExtract.toDissolvedSymbol());
		return coefficient.toFraction(); ///TODO add in dissolvedSymbol a field which specify if the symbol was
										 ///in fraction form or in powers form.
	}
	
	public final Symbol getPolynomCoefficient(Symbol extractedSym) {
		return new PolynomCoefficient (this.toDissolvedSymbol(), extractedSym.toDissolvedSymbol()).getCoefficient();
	}
	
	///*Meant to be overriden only by Addition class
	///In an addition, returns extractedSym's coefficients sum in any one of the addition arguments.
	///For example: extract x from x^2 + 5x + 2x + y + 2 --> returns 5+2 (=7)
	public Symbol getTotalPolynomCoefficients(Symbol extractedSym) {
		return this.getPolynomCoefficient(extractedSym);
	}
	
	public Symbol openForm(MutableBoolean tookAction) {
		return openForm.openForm(this, tookAction);
	}
	
	public Symbol openForm() {
		MutableBoolean placeholder = new MutableBoolean();
		return this.openForm(placeholder);
	}
	
	public boolean isOpenForm() {
		return openForm.isOpen(this);
	}
	
	public boolean containsVariable(Variable v) {
		List<Symbol> params = this.getParams();
		if (params != null) {
			for (Symbol nextP : params) if (nextP.containsVariable(v)) return true;
		}
		return false;
	}
	
	/*public boolean isAssociative() { //returns true if for func f : f(s,s, f(s1,s2)) = f(s,s,s1,s2)
		return false;
	}
	public boolean isOrderedParams() { //returns true if the ordering of the parameters is meaningful.
		return false;
	}*/
	
	protected boolean paramsEqual(Symbol otherSym) {
		List<Symbol> thisParams = this.getParams();
		List<Symbol> otherParams = otherSym.getParams();
		boolean thisParamNull = thisParams == null;
		boolean otherParamNull = otherParams == null;
		if (thisParamNull || otherParamNull) return thisParamNull && otherParamNull;
		
		return parameters.parameterEquals(otherParams);
		
	}
	
	private boolean equalID(CalcNumber otherId) {
		return otherId == null ? this.id() == null : otherId.equals(this.id());
	}
	
	///equates the symbols types, id numbers and parameters (equates parameters via this symbol ParameterEquator)
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (obj instanceof Symbol) {
			Symbol otherSym = (Symbol) obj;
			if (otherSym.getType().equals(this.getType()) &&
				equalID(otherSym.id()) &&
				paramsEqual(otherSym)) return true;
			else return false;
		}
		else return false;
	}
	
	private int hashField(Object field) {
		return field == null ? 0 : field.hashCode();
	}
	
	@Override
	public int hashCode() {
		int paramsHash = parameters.parametersHashCode();
		int idHash = symIdType.idHash();
		int typeHash = getType().hashCode();
		//System.out.println("hash: " + paramsHash + " " + idHash + " " + typeHash);
		return Objects.hash(paramsHash, idHash, typeHash);
	}
	
	public boolean isZero() {
		return this.equals(NumberSym.zero);
	}
	
	public boolean isRawNumber() {
		return this.getIdentifier().isRawNumber().getIsNum();
	}
	
	public boolean isImplicatedNumber() {
		return this.getIdentifier().isImplicatedNumber();
	}
	
	///UNTESTED, may contain errors
	private List<Symbol> copyParams() {
		List<Symbol> thisParams = parameters.getParameters();
		if (thisParams == null) return null;
		
		LinkedList<Symbol> buff = new LinkedList<Symbol>();
		for (Symbol param : thisParams) buff.add(param.deepCopy());
		return buff;
	}
	
	///UNTESTED, may contain errors
	@Override
	public Symbol deepCopy() {
		Symbol newSym = copySymbolType();
		newSym.setParams(copyParams());
		newSym.setId(this.id());
		return newSym;
	}
}


