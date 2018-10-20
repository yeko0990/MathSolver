package yekocalc;

import java.util.LinkedList;
import java.util.List;

abstract class PolynomIdentifier {
	protected Symbol s;
	protected PolynomInfo inf;

	public PolynomIdentifier(Symbol sP) {
		s = sP;
		inf = PolynomInfo.emptyPolynom();
	}
	
	protected PolynomInfo getChildPolynomInfo(Symbol child) {
		PolynomInfo buff;
		buff = child.symDeterminator().isPolynom();
		return buff;
	}
	
	///Returns the polynom infos of all parameters of symbol s
	protected List<PolynomInfo> getChildrenPolynomInfos() {
		List<Symbol> params = s.getParams();
		List<PolynomInfo> buff = new LinkedList<PolynomInfo>();
		for (int i = 0; i < params.size(); i++) {
			buff.add(getChildPolynomInfo(params.get(i)));
		}
		return buff;
	}
	
	protected boolean isEveryChildPolynom(List<PolynomInfo> children) {
		for (int i = 0; i < children.size(); i++) {
			if (!children.get(i).getIsPolynom()) return false;
		}
		return true;
	}
	
	protected void registerNotPolynom() {
		inf.makeNotPolynom();
	}
	
	public PolynomInfo getInfo() {
		return inf;
	}
	
	///Here, we only need to return false if not polynom. The wrapper
	///function below will write down that info to 'out'.
	public abstract PolynomInfo isPolynom();
}