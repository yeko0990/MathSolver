package yekocalc;

public class RootPolynomIdentifier extends PolynomIdentifier {
	Root root;
	public RootPolynomIdentifier(Root root) {
		super(null); ///TODO refactor super class (PolynomIdentifier) so it does not contain a Symbol member....
		this.root = root;
	}

	@Override
	public PolynomInfo isPolynom() {
		if (!root.base().isRawNumber() || !root.rootBy().isRawNumber()) return PolynomInfo.notPolynom();
		//if (!root.isImplicatedNumber()) return PolynomInfo.notPolynom();
		
		PolynomInfo info = PolynomInfo.emptyPolynom();
		if (!root.base().equals(NumberSym.zero)) info.setZeroDeg(true);
		else info.setZeroDeg(false);
		return info;
	}

}
