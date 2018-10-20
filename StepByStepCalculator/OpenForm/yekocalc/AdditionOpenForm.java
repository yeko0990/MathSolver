package yekocalc;

import java.util.List;

public class AdditionOpenForm extends OpenForm {
	
	@Override
	public Symbol openForm(Symbol sym, MutableBoolean tookAction) {
		List<Symbol> params = sym.getParams();
		MutableBoolean acted = new MutableBoolean(false);
		for (int i = 0; i < params.size(); i++) {
			params.set(i, params.get(i).openForm(acted));
			if (acted.value()) tookAction.set(true);
		}
		sym = sym.standarizedForm();
		return sym;
	}
	
	@Override
	public boolean isOpen(Symbol sym) {
		List<Symbol> params = sym.getParams();
		for (int i = 0; i < params.size(); i++) {
			if (!params.get(i).isOpenForm()) return false;
		}
		return true;
	}
}
