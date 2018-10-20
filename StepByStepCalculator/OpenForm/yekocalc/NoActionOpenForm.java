package yekocalc;

public class NoActionOpenForm extends OpenForm {

	public NoActionOpenForm() {
		
	}

	@Override
	public Symbol openForm(Symbol sym, MutableBoolean tookAction) {
		tookAction.set(false);
		sym = sym.standarizedForm();
		return sym;
	}

	@Override
	public boolean isOpen(Symbol sym) {
		return true;
	}

}
