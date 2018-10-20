package yekocalc;

public class RootTranslatedNumber extends TranslatedNumberIdentifier {
	Root root;
	
	public RootTranslatedNumber(Root root) {
		super(null); ///TODO refactor TranslatedNumberIdentifier class so it wont contain a Symbol member.
		this.root = root;
	}
	
	private NumberInfo calculateRoot(CalcNumber base, CalcNumber rootBy) {
		NumberInfo info = new NumberInfo();
		info.setIsNum(true);
		info.setNum(base.root(rootBy));
		return info;
	}

	@Override
	public NumberInfo isTranslatedNumber() {
		if (root.isImplicatedNumber()) return NumberInfo.notNumber();
		
		NumberInfo baseInf, rootByInf;
		baseInf = root.base().getIdentifier().isTranslatedNumber();
		rootByInf = root.rootBy().getIdentifier().isTranslatedNumber();
		
		if (!rootByInf.getIsNum() || !baseInf.getIsNum()) return NumberInfo.notNumber();
		if (!rootByInf.getNum().isInteger()) return NumberInfo.notNumber();
		
		else return calculateRoot(baseInf.getNum(), rootByInf.getNum());
	}

}
