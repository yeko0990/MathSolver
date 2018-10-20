package yekocalc;

///A root is a number if its BASE cannot be simplified
public class RootNumberIdentifier extends NumberIdentifier {
	private Root root;
	public RootNumberIdentifier(Root rootSym) {
		this.root = rootSym;
	}

	private NumberInfo getRootNumberInfo(CalcNumber baseNum, CalcNumber rootBy) {
		return new NumberInfo(baseNum.root(rootBy));
	}
	
	@Override
	public NumberInfo isRawNumber() {
		NumberInfo baseInf = root.base().getIdentifier().isRawNumber();
		NumberInfo rootByInf = root.rootBy().getIdentifier().isRawNumber();
		if (!baseInf.getIsNum() || !rootByInf.getIsNum()) return NumberInfo.notNumber();
		else return getRootNumberInfo(baseInf.getNum(), rootByInf.getNum());
	}

}
