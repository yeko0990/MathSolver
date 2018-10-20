package yekocalc;
import java.util.*;

///Sums parameters which are numbers and replaces them with a sum result.
public class SumNumberSimplifier implements DirectSimplifier {
	private Addition addition;
	private SymbolFilter<NumberInfo> numFilter;
	
	public SumNumberSimplifier(Addition addition, SymbolFilter<NumberInfo> filter) {
		this.addition = addition;
		numFilter = filter;
	}
	
	@Override
	public boolean isLightSimplification() {
		return false;
	}
	
	private CalcNumber sumNumbers(List<SymbolFilterResult<NumberInfo>> symNums) {
		CalcNumber buff = symNums.get(0).info.getNum();
		for (int i = 1; i < symNums.size(); i++) {
			buff = buff.add(symNums.get(i).info.getNum());
		}
		return buff;
	}
	
	@Override
	public boolean simplifyNext() {
		List<SymbolFilterResult<NumberInfo>> symNums = SymbolFilterUtils.getFilterResult(addition.getParams(), numFilter);
		if (symNums.size() <= 1) return false;

		CalcNumber res = sumNumbers(symNums);
		SymbolFilterResult.removeSymbols(addition.getParams(), symNums);
		addition.getParams().add(new NumberSym(res));
		//LinkedList<Symbol> newParams = new LinkedList<Symbol>();
		//newParams.add(new NumberSym(res));
		//addition.setParams(newParams);
		return true;
	}

}
