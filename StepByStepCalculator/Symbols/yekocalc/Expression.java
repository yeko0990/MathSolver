package yekocalc;

///TODO:
///IMPORTANT TO SOLVE A CRITICAL BUG:
///Move repeating of standarization in here, rather than SymbolSImplifier repeat standarization until done.

///A wrapper class for symbol, abstracts standarization of a symbol
///(and more symbol functions such as simplification)
public class Expression {
	Symbol storedSym;
	public Expression(Symbol s) {
		storedSym = s;
	}
	
	public void standarize() {
		storedSym = storedSym.standarizedForm();
	}
	
	public boolean simplify() {
		standarize();
		MutableBoolean acted = new MutableBoolean(false);
		this.setStoredSym (storedSym.simplifiedForm(acted));
		return acted.value();
	}
	
	public boolean simplifyNext() {
		standarize();
		MutableBoolean acted = new MutableBoolean(false);
		this.setStoredSym (storedSym.nextSimplifyForm(acted));
		return acted.value();
	}
	
	public boolean lightSimplify() {
		standarize();
		MutableBoolean acted = new MutableBoolean(false);
		this.setStoredSym (storedSym.lightSimplifiedForm(acted));
		return acted.value(); 
	}
	
	public boolean lightSimplifyNext() {
		standarize();
		MutableBoolean acted = new MutableBoolean(false);
		this.setStoredSym (storedSym.nextLightSimplifyForm(acted));
		return acted.value();
	}
	
	public void toOpenForm() {
		setStoredSym(storedSym.openForm());
	}
	
	public Symbol getStoredSym() {
		return storedSym;
	}
	
	public void setStoredSym(Symbol newSym) {
		storedSym = newSym;
	}
}
