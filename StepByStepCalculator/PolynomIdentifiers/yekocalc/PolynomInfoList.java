package yekocalc;

import java.util.List;

class PolynomInfoList {
	List<PolynomInfo> infos;
	private void init(List<PolynomInfo> infosP) {
		infos = infosP;
	}
	public PolynomInfoList(List<PolynomInfo> infosP) {
		init(infosP);
	}
	
	///Takes all variables in the list of infos, and creates an info that contains every variable (with a degree of 0).
	///public PolynomInfo extendAllVariables() {
	///	PolynomInfo buff = new PolynomInfo();
	///}
	
	///Returns the highest degree of the variable v
	public int variableHighestDegree(Variable v) {
		int highest = 0;
		for (int i = 0; i < infos.size(); i++) {
			int currDeg = infos.get(i).getInfo(v).getMaxDegree();
			highest = currDeg > highest ? currDeg : highest;
		}
		return highest;
	}
	
	/*private void extendAllVarsDegree(PolynomInfo outInf, PolynomInfo extendingInf) {
		PolynomVarInfo next;
		Variable nextVar;
		int nextDeg;
		PolynomVarInfo nextExtendedInfo;
		int nextExtendedDeg;
		for (int i = 0; i < extendingInf.varCount(); i++) {
			next = extendingInf.getInfo(i);
			nextVar = next.getVariable();
			nextDeg = next.getMaxDegree();
			nextExtendedInfo = outInf.getInfo(nextVar);
			nextExtendedDeg = nextExtendedInfo.getMaxDegree();
			nextExtendedInfo.setMaxDegree(nextDeg > nextExtendedDeg ? nextDeg : nextExtendedDeg);
			
			outInf.setInfo(nextExtendedInfo);
		}
	}*/
	
	/*public PolynomInfo allVariablesHighestDegree() {
		PolynomInfo buff = PolynomInfo.emptyPolynom();
		for (int i = 0; i < infos.size(); i++) {
			extendAllVarsDegree(buff, infos.get(i));
		}
		return buff;
	}*/
	
	///Adds every variable degree in 'adding' to each of 'addedTo' variable degrees.
	/*private void addAllVariables(PolynomInfo addedTo, PolynomInfo adding) {
		PolynomVarInfo nextVar;
		int nextDeg;
		PolynomVarInfo varInfo;
		int currDeg;
		for (int i = 0; i < adding.varCount(); i++) {
			nextVar = adding.getInfo(i);
			nextDeg = nextVar.getMaxDegree();
			varInfo = addedTo.getInfo(nextVar.getVariable());
			currDeg = varInfo.getMaxDegree();
			varInfo.setMaxDegree(currDeg + nextDeg);
		}
	}*/
	
	///For each variable, adds its degrees in the all of the infos of the list.
	/*public PolynomInfo addDegrees() {
		PolynomInfo buff = PolynomInfo.emptyPolynom();
		for (int i = 0; i < infos.size(); i++) {
			addAllVariables(buff, infos.get(i));
		}
		return buff;
	}*/
}