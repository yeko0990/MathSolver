package yekocalc;
import java.util.*;

public class SymbolFilterUtils {
	public static <T> List<SymbolFilterResult<T>> getFilterResult(List<Symbol> toFilter, SymbolFilter<T> filter) {
		SymbolListFilter<T> numberFilter = new SymbolListFilter<T>(toFilter, filter);
		return numberFilter.filter();
	}
}
