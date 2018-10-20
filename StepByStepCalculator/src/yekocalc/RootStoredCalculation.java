package yekocalc;

///Save root calculations and register recent calculations.
///Used to prevent repeating the same calculation (for example simplification) of a root even though the
///root does not change.
///R: result of the calculation, example: CalcNumber (if calculating root of a number), boolean (if calculating whether
///	  the root is an implicated number), e.g. 
public class RootStoredCalculation<R> {
	Symbol storedBase, storedRootBy;
	R result;
	
	public RootStoredCalculation() {
		
	}
	
	public boolean isStoringCalculation(Root root) {
		if (storedBase == null || storedRootBy == null) return false;
		return root.base().equals(storedBase) && root.rootBy().equals(storedRootBy);
	}
	
	public void storeCalculation(Root root, R resultStored) {
		storedBase = root.base().deepCopy();
		storedRootBy = root.rootBy().deepCopy();
		result = resultStored;
	}
	
	public R getResult() {
		return result;
	}

}
