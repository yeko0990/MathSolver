package yekocalc;
import java.util.*;

///Extracts symbol parameters which have some characteristic, for example numbers.
///T: Info object linked to the searched characteristic
class SymbolListFilter<T> {
	List<Symbol> list;
	SymbolFilter<T> sf;
	
	public SymbolListFilter(List<Symbol> listP, SymbolFilter<T> filterMethod) {
		list = listP;
		sf = filterMethod;
	}
	
	public List<SymbolFilterResult<T>> filter() {
		List<SymbolFilterResult<T>> buff = new LinkedList<SymbolFilterResult<T>>();
		for (int i = 0; i < list.size(); i++) {
			Symbol sym = list.get(i);
			T nextInfo = sf.filter(sym);
			if (sf.isMatching(nextInfo)) buff.add(new SymbolFilterResult<T>(sym, nextInfo, i));
		}
		return buff;
	}
}

///Holds an info object of type T, a symbol that the info is referring to and the symbol index in the queried list
class SymbolFilterResult<T> {
	public static <T> void removeSymbols(List<Symbol> list, List<SymbolFilterResult<T>> toRemove) {
		for (SymbolFilterResult<T> info : toRemove) {
			list.remove(info.sym);
		}
	}
	
	public Symbol sym;
	public T info;
	public int index;
	public SymbolFilterResult(Symbol sP, T infoP, int indexP) {
		sym = sP;
		info = infoP;
		index = indexP;
	}
}