package yekocalc;

///An open form is a form of a symbol in which every symbol cannot be "opened" to an addition OR to a multiplication
///3x^2 + 3x is open
///3x(x + 1) is not open
///3^2 * x^2 is open
///(3*x)^2 is not open
public abstract class OpenForm {
	//protected Symbol sym;
	//public OpenForm(Symbol s) {
	//	sym = s;
	//}
	
	///Sets sym to be an open form symbol.
	///writes to tookAction if sym was changed. 
	public abstract Symbol openForm(Symbol sym, MutableBoolean tookAction);
	
	///checks whether the symbol is open
	public abstract boolean isOpen(Symbol sym); 
}