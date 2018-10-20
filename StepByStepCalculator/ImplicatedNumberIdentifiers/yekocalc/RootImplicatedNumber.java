package yekocalc;

public class RootImplicatedNumber extends ImplicatedNumberIdentifier {
	///Here, we save the result of a root calculation, so we don't have to calculate the root
	///of the same symbol more than once.
	RootStoredCalculation<Boolean> storedResult;
	
	Root root;
	
	public RootImplicatedNumber(Root root) {
		this.root = root;
		storedResult = new RootStoredCalculation<Boolean>();
	}
	
	private boolean returnAndStore(boolean ret) {
		storedResult.storeCalculation(this.root, ret);
		return ret;
	}
	
	///Check whether the root of the base is PRECISE: root.pow(rootBy) == base.
	private boolean isRootPrecise(CalcNumber baseNum, CalcNumber rootByNum) {
		CalcNumber rootResult = baseNum.root(rootByNum);
		return rootResult.pow(rootByNum).equals(baseNum);
	}
	
	@Override
	public boolean isImplicatedNumber() {
		///TODO very temporary
		return false;
		
		//if (storedResult.isStoringCalculation(this.root)) return storedResult.getResult();
		
		//NumberInfo baseInf = root.base().getIdentifier().isRawNumber();
		//NumberInfo rootByInf = root.rootBy().getIdentifier().isRawNumber();
		
		//if (!baseInf.getIsNum() || !rootByInf.getIsNum()) return returnAndStore(false);
		//if (!rootByInf.getNum().isInteger()) return returnAndStore(false);
		
		//return returnAndStore(isRootPrecise(baseInf.getNum(), rootByInf.getNum()));
	}

}
