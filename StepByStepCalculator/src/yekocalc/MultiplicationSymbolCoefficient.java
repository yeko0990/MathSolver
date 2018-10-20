package yekocalc;

import java.util.LinkedList;
import java.util.List;

public class MultiplicationSymbolCoefficient {
	Symbol main, extractedSym;
	List<Symbol> params, coefficients, symsToExtract;
	
	public MultiplicationSymbolCoefficient(Symbol mainP, Symbol toExtract) {
		main = mainP;
		extractedSym = toExtract;
	}
	
	private void initVars(List<Symbol> symsToExtractP) {
		params = main.getParams();
		coefficients = new LinkedList<Symbol>();
		symsToExtract = symsToExtractP;
	}
	
	/*private LinkedList<Symbol> deepCopyList(List<Symbol> copiedList) {
		LinkedList<Symbol> buff = new LinkedList<Symbol>();
		for (Symbol nextSym : copiedList) {
			buff.add(nextSym.deepCopy());
		}
		return buff;
	}
	
	private LinkedList<Symbol> shallowCopyList(List<Symbol> toCopy) {
		LinkedList<Symbol> buff = new LinkedList<Symbol>();
		for (Symbol next : toCopy) buff.add(next);
		return buff;
	}
	
	///Returns index of sym in the list. -1 if not found
	private int symbolIndex(List<Symbol> list, Symbol sym) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).equals(sym)) return i;
		}
		return -1;
	}

	///Returns false if not all symbols are contained in the list
	private boolean removeSymbols(List<Symbol> list, List<Symbol> toRemove) {
		int nextIndex;
		for (Symbol nextSym : toRemove) {
			nextIndex = symbolIndex(list, nextSym);
			if (nextIndex == -1) return false;
			else list.remove(nextIndex);
		}
		return true;
	}*/
	
	/*private Symbol getCoefficientGeneral(Symbol extractedSym) {
		int index = symbolIndex(this.getParams(), extractedSym);
		if (index == -1) return NumberSym.doubleSymbol(0);
		else {
			List<Symbol> coefficientList = deepCopyList(this.getParams());
			removeSymbols(coefficientList, ListUtils.toLinkedList(extractedSym));
			if (coefficientList.size() == 0) return NumberSym.doubleSymbol(1);
			return coefficientList.size() == 1 ? coefficientList.get(0) : new Multiplication(coefficientList);
		}
	}*/
	
	///Does not add coefficient if it is 1 (because it wont effect final result)
	private void addCoefficient(Symbol coef) {
		if (coef.equals(NumberSym.doubleSymbol(1))) return;
		else coefficients.add(coef);
	}
	
	///Returns the DIRECT coefficient of a symbol, so that multiplying the symbol in the coefficient
	///will result in the symbol that the coeffficient has been extracted from.
	///(getCoefficient of x for x+6, for example, will result in 1 while the EFFECTIVE coefficient is 0)
	private Symbol getEffectiveCoefficient(Symbol sym, Symbol extractedSym) {
		if (sym.getType() == SymType.Addition)
			return sym.equals(extractedSym) ? NumberSym.doubleSymbol(1) : NumberSym.zero;
		else return sym.getCoefficient(extractedSym);
	}
	
	///Tries to extract one of the symbols from the next parameter and to add the coefficient.
	///If failed, adds the next parameter to the coefficients.
	private void tryExtractFromParam(Symbol nextParam) {
		Symbol nextCoef;
		for (int i = 0; i < symsToExtract.size(); i++) {
			nextCoef = getEffectiveCoefficient(nextParam, symsToExtract.get(i));
			if (!nextCoef.equals(NumberSym.zero)) {
				addCoefficient(nextCoef);
				symsToExtract.remove(i);
				return;
			}
		}
		coefficients.add(nextParam);
	}
	
	private Symbol tryGetCoefficient() {
		for (int i = 0; i < params.size(); i++) {
			Symbol nextParam = params.get(i);
			tryExtractFromParam(nextParam);
		}
		if (symsToExtract.isEmpty()) return new Multiplication(coefficients);
		else return NumberSym.zero;
		
	}
	
	public Symbol getCoefficient() {
		if (extractedSym.getType() == SymType.Multiplication) {
			initVars(extractedSym.getParams());
			return tryGetCoefficient();
		}
		
		else {
			initVars(ListUtils.toLinkedList(extractedSym));
			return tryGetCoefficient();
		}
	}
}