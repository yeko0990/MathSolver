package yekocalc;

import java.util.List;

public class ParameterUnorderedEquator extends ParameterEquator {
	private int indexOfElement(Symbol element, List<Symbol> list, boolean[] found) {
		for (int i = 0; i < list.size(); i++) {
			if (element.equals(list.get(i)) && !found[i]) return i;
		}
		return -1;
	}
	
	@Override
	protected boolean internalEquals(List<Symbol> params1, List<Symbol> params2) {
		if (params1.size() != params2.size()) return false;
		
		boolean[] marked = new boolean[params1.size()];
		int nextIndex;
		for (int i = 0; i < params1.size(); i++) {
			nextIndex = indexOfElement(params1.get(i), params2, marked);
			if (nextIndex == -1) return false;
			else marked[nextIndex] = true;
		}
		return true;
	}
	
	@Override
	public int parametersHashCode(List<Symbol> params) {
		return ListUtils.unorderedHashCode(params);
	}
}
