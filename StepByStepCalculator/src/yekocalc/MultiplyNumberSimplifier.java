package yekocalc;
import java.util.*;

///Takes every number parameter and replaces them with a multiplication of the numbers.
///mul(5,6,3) -> mul(90)
///*If finds a parameter that equals 0, removes ALL parameters and set the only parameter to be 0.
public class MultiplyNumberSimplifier implements DirectSimplifier {
	private Multiplication mul;
	private SymbolFilter<NumberInfo> numberFilter;
	
	public MultiplyNumberSimplifier(Multiplication mul, SymbolFilter<NumberInfo> filter) {
		numberFilter = filter;
		this.mul = mul;
	}
	
	@Override
	public boolean isLightSimplification() {
		return true;
	}
	
	private Symbol multiplyRes(List<SymbolFilterResult<NumberInfo>> res) {
		CalcNumber next, curr = res.get(0).info.getNum();
		for (int i = 1; i < res.size(); i++) {
			next = res.get(i).info.getNum();
			curr = curr.mul(next);
		}
		return new NumberSym(curr);
	}
	
	private boolean containsZero(List<SymbolFilterResult<NumberInfo>> nums) {
		for (SymbolFilterResult<NumberInfo> n : nums) {
			if (n.info.getNum().equals(CalcNumber.ZERO)) return true;
		}
		return false;
	}
	
	private void makeSymbolZero(Symbol s) {
		s.getParams().clear();
		s.getParams().add(NumberSym.zero);
	}
	
	@Override
	public boolean simplifyNext() {
		SymbolListFilter<NumberInfo> numFilter = new SymbolListFilter<NumberInfo>(mul.getParams(), numberFilter);
		List<SymbolFilterResult<NumberInfo>> res = numFilter.filter();
		
		if (containsZero(res)) {
			makeSymbolZero(mul);
			return true;
		}
		
		if (res.size() <= 1) return false;
		
		SymbolFilterResult.removeSymbols(mul.getParams(), res);
		mul.getParams().add(multiplyRes(res));
		return true;
	}

}
