package yekocalc;

import java.util.List;

public class TryConvertPlusMinus implements SymbolVisitor {
	private Pair<Symbol> split;
	
	public TryConvertPlusMinus() {
		
	}

	///Returns null on fail
	public Pair<Symbol> tryConvert(Symbol sym) {
		sym.accept(this);
		return split;
	}
	
	private Symbol replaceParam(Symbol fullSymbol, Symbol replaceBy, int paramIndex) {
		Symbol buff = fullSymbol.deepCopy();
		//buff.getParams().remove(paramIndex);
		
		//if (paramIndex < buff.getParams().size()) buff.getParams().set(paramIndex, replaceBy);
		//else buff.getParams().add(replaceBy);
		buff.getParams().set(paramIndex, replaceBy);
		return buff;
	}
	
	private void splitParam(Symbol fullSymbol, Symbol param, Symbol o1, Symbol o2, int paramIndex) {
		split.o1 = replaceParam(fullSymbol, o1, paramIndex);
		split.o2 = replaceParam(fullSymbol, o2, paramIndex);
		
	}
	
	///Tries to convert a param, if succeeds than 'split' is set to NOT null.
	private void tryConvertParams(Symbol sym) {
		List<Symbol> params = sym.getParams();
		Symbol next;
		for (int i = 0; i < params.size(); i++) {
			next = params.get(i);
			next.accept(this);
			if (split != null) {
				splitParam(sym, next, split.o1, split.o2, i);
				return;
			};
		}
		split = null;
	}
	
	@Override
	public void visit(NumberSym sym) {
		split = null;
	}

	@Override
	public void visit(Variable sym) {
		split = null;
	}

	@Override
	public void visit(Addition sym) {
		tryConvertParams(sym);
	}

	@Override
	public void visit(Multiplication sym) {
		tryConvertParams(sym);
	}

	@Override
	public void visit(Power sym) {
		tryConvertParams(sym);
	}

	@Override
	public void visit(Fraction sym) {
		tryConvertParams(sym);
	}

	@Override
	public void visit(Root sym) {
		tryConvertParams(sym);
	}

	@Override
	public void visit(PlusMinus sym) {
		Symbol option1 = sym.parameter().deepCopy();
		
		Symbol option2 = new Multiplication(NumberSym.intSymbol(-1), sym.parameter().deepCopy());
		option2 = option2.standarizedForm();
		
		split = new Pair<Symbol>(option1, option2);
	}

}
