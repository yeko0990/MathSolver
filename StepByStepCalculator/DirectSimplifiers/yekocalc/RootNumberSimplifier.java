package yekocalc;

import java.util.List;

public class RootNumberSimplifier implements DirectSimplifier {
	private Root root;
	
	public RootNumberSimplifier(Root root) {
		this.root = root;
	}

	@Override
	public boolean isLightSimplification() {
		return false;
	}

	private void calculateRoot(CalcNumber base, CalcNumber rootBy) {
		root.setBase(new NumberSym(base.root(rootBy)));
		root.setRootBy(NumberSym.intSymbol(1));
	}
	
	@Override
	public boolean simplifyNext() {
		if (root.isImplicatedNumber()) return false; ///IMPORTANT- we do not want to simplify sqrt(3), for example.
		
		NumberInfo baseInf, rootByInf;
		baseInf = root.base().getIdentifier().isRawNumber();
		rootByInf = root.rootBy().getIdentifier().isRawNumber();
		
		if (!baseInf.getIsNum() || !rootByInf.getIsNum()) return false;
		if (!rootByInf.getNum().isInteger()) return false;
		
		calculateRoot(baseInf.getNum(), rootByInf.getNum());
		return true;
	}

}
