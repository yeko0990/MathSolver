package yekocalc;

public class PowerOpenForm extends OpenForm {

	private Symbol multiplyAdditions(Symbol add1, Symbol add2) {
		Symbol multiply = new Multiplication(add1, add2); 
		return multiply.openForm();
	}
	
	///TODO Potentially extremely heavy performace tax if expo is very big. Consider defending a crash here.
	private Symbol openAdditionByNumExponent(Symbol addition, int expo) {
		if (expo == 0) return NumberSym.doubleSymbol(1);
		
		Symbol current = addition;	///TODO DANGEROUS- basically we should do a deep copy of addition but it should
									///work as we only use this symbol for opening. *WORTH CHECKING IT CAREFULLY*
		
		for (int i = 1; i < expo; i++) {
			current = multiplyAdditions(current, addition);
		}
		return current;
	}
	
	///TODO probably should refactor... We assume sym is a power, better to somehow take a Power as a parameter.
	///		Maybe make it an overriden Symbol method...?
	private Symbol openAdditionBase(Symbol sym, MutableBoolean tookAction) {
		Symbol base = base(sym);
		Symbol exponent = exponent(sym);
		NumberInfo numExpo = exponent.getIdentifier().isRawNumber();
		if (!numExpo.getIsNum()) return sym;
		CalcNumber num = numExpo.getNum();
		if (!num.isInteger()) return sym;
		
		int expoNum = (int)num.getDouble();
		if (expoNum < 0) return sym; ///TODO maybe extend functionality so we can handle negative integer exponents
		return openAdditionByNumExponent(base, expoNum);
	}
	
	private Symbol base(Symbol s) {
		return s.getParams().get(0);
	}
	
	private Symbol exponent(Symbol s) {
		return s.getParams().get(1);
	}
	
	@Override
	public Symbol openForm(Symbol sym, MutableBoolean tookAction) {
		if (base(sym).getType() == SymType.Addition) return openAdditionBase(sym, tookAction);
		
		DissolvedSymbol dissolved = sym.toDissolvedSymbol();
		return dissolved.toPowers(); ///TODO make some field in dissolvedSymbol that specify whether the symbol was
									   ///translated from power form or from fraction form. So we can know if we want
									   ///the open form as a fraction or as powers
									   ///EXAMPLE: we want (1/x)^2 as 1/(x^2), not x^(-2).
	}

	private boolean multiBase(Symbol sym) {
		return this.base(sym).getType() == SymType.Multiplication;
	}
	
	private boolean additionBaseNumberExponent(Symbol sym) {
		return base(sym).getType() == SymType.Addition && exponent(sym).isRawNumber(); ///TODO AND exponent is an integer
																					   ///(pretty important)
	}
	
	@Override
	public boolean isOpen(Symbol sym) {
		return !multiBase(sym) && !additionBaseNumberExponent(sym);
	}

}
