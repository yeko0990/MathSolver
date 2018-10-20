package yekocalc;

import java.util.LinkedList;
import java.util.List;

///Mixes a list of polynominfos, so that if and only if degree i of variable v exists in one or more of the infos, than
///it will exist in the returned info.
class PolynomMixer {
	private List<PolynomInfo> infos;
	private PolynomInfo ret;
	private VarPolynomMixer mixer;
	
	public PolynomMixer(List<PolynomInfo> infosP) {
		infos = infosP;
		ret = PolynomInfo.emptyPolynom();
		mixer = new VarPolynomMixer();
	}
	
	public PolynomMixer(PolynomInfo...infosP) {
		this(arrToList(infosP));
	}
	
	static private LinkedList<PolynomInfo> arrToList(PolynomInfo[] arr) {
		LinkedList<PolynomInfo> list = new LinkedList<PolynomInfo>();
		for (int i = 0; i < arr.length; i++) {
			list.add(arr[i]);
		}
		return list;
	}
	
	private void mixInfo(PolynomInfo nextInfo) {
		mixer.mixZeroDeg(nextInfo);
		for (int i = 0; i < nextInfo.varCount(); i++) {
			mixer.mixInfo(nextInfo.getInfo(i));
		}
	}
	
	public PolynomInfo mixPolynoms() {
		for (int i = 0; i < infos.size(); i++) {
			mixInfo(infos.get(i));
		}
		return mixer.getInfo();
	}
}