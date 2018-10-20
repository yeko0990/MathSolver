package yekocalc;
import java.util.*;

///Contains methods for each identifier class (isNumber, isPolynom...)
class SymbolPatternIdentifier {
	NumberIdentifier numId;
	ImplicatedNumberIdentifier impNumId;
	TranslatedNumberIdentifier transNumId;
	PolynomIdentifier polyId;
	
	public SymbolPatternIdentifier(NumberIdentifier numIdP, ImplicatedNumberIdentifier impNumId, TranslatedNumberIdentifier transNumIdP,
								PolynomIdentifier polyIdP) {
		numId = numIdP;
		this.impNumId = impNumId;
		transNumId = transNumIdP;
		polyId = polyIdP;
	}
	
	public PolynomInfo isPolynom() {
		return polyId.isPolynom();
	}
	public NumberInfo isRawNumber() {
		return numId.isRawNumber();
	}
	///TODO Maybe refactor so returns NumberInfo instead of boolean. May be useful.
	public boolean isImplicatedNumber() {
		return impNumId.isImplicatedNumber();
	}
	public NumberInfo isTranslatedNumber() {
		return transNumId.isTranslatedNumber();
	}
}

///Base class for identifiers
//abstract class GeneralIdentifier {
//	protected Symbol s;
//	
//}

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

class PolynomDegrees {
	private HashMap<Integer, Boolean> degrees;
	private static final Boolean defaultVal = false;
	
	public static PolynomDegrees parse(boolean[] arr) {
		PolynomDegrees newDegs = new PolynomDegrees();
		for (int i = 0; i < arr.length; i++) {
			newDegs.setDegree(i, arr[i]);
		}
		return newDegs;
	}
	
	public PolynomDegrees() {
		degrees = new HashMap<Integer,Boolean>();
	}
	public boolean getDegree(int deg) {
		return degrees.containsKey(deg) ? degrees.get(deg) : defaultVal;
	}
	public void setDegree(int deg, boolean val) {
		degrees.put(deg, val);
	}
	
	public PolynomDegrees copy(int maxDeg) {
		PolynomDegrees newDegs = new PolynomDegrees();
		for (int i = 0; i <= maxDeg; i++) {
			newDegs.setDegree(i, this.getDegree(i));
		}
		return newDegs;
	}
	
}

class PolynomInfo {
	private boolean isAPolynom;
	private List<PolynomVarInfo> vars;
	private boolean zeroDegree; //A zero degree is not related to a specific variable
								//(x^0 = y^0 = 1)
	
	public static PolynomInfo emptyPolynom() {
		PolynomInfo buff = new PolynomInfo();
		buff.makeEmptyPolynom();
		return buff;
	}
	
	public static PolynomInfo notPolynom() {
		return new PolynomInfo(null, false, false);
	}
	
	public void makeNotPolynom() {
		this.init(new LinkedList<PolynomVarInfo>(), false, false);
	}
	
	public void makeEmptyPolynom() {
		this.init(new LinkedList<PolynomVarInfo>(), false, true);
	}
	
	private void init(List<PolynomVarInfo> varsP, boolean zeroDegree, boolean isPolynomP) {
		vars = varsP;
		isAPolynom = isPolynomP;
	}
	
	//Returns -1 on fail
	public int varIndex(Variable v) {
		for (int i = 0; i < vars.size(); i++) {
			if (v.equals(vars.get(i).getVariable())) return i;
		}
		return -1;
	}
	
	private void addVarInfo(PolynomVarInfo inf) {
		vars.add(inf);
	}
	
	private void updateVarInfo(int index, PolynomVarInfo newInf) {
		vars.set(index, newInf);
	}
	
	public PolynomInfo(List<PolynomVarInfo> varsP, boolean zeroDegreeP, boolean isPolynomP) {
		init(varsP, zeroDegreeP, isPolynomP);
	}
	
	public PolynomInfo(List<PolynomVarInfo> varsP, boolean zeroDegreeP) {
		init(varsP, zeroDegreeP, true);

	}
	
	public PolynomInfo() {
		init(new LinkedList<PolynomVarInfo>(), false, true);

	}
	
	public boolean getIsPolynom() {
		return isAPolynom;
	}
	
	public void setZeroDeg(boolean val) {
		zeroDegree = val;
	}
	public boolean getZeroDeg() {
		return zeroDegree;
	}
	
	public void setInfo(PolynomVarInfo inf) {
		Variable varInfo = inf.getVariable();
		int index = varIndex(varInfo);
		if (index == -1) addVarInfo(inf);
		else updateVarInfo(index, inf);
	}
	
	public PolynomVarInfo getInfo(Variable var) throws IllegalArgumentException {
		int index = varIndex(var);
		if (index == -1) {
			return new PolynomVarInfo(var, 0);
		}
		else return vars.get(index);
	}
	
	public PolynomVarInfo getInfo(int index) {
		return vars.get(index);
	}
	
	public int varCount() {
		return vars.size();
	}
}

class PolynomVarInfo {
	private Variable v;
	private int maxDegree; ///max degree of the variable
	PolynomDegrees degrees; ///stores whether a certain degree exists in this polynom
									
	//private void initDegs(boolean[] degsP) {
	//	degrees = PolynomDegrees.parse(degsP);
	//}
	
	private void initDegs(PolynomDegrees degsP) {
		degrees = degsP;
	}
	
	private void init(int deg, Variable vP, PolynomDegrees degsP) {
		maxDegree = deg;
		v = vP;
		initDegs(degsP);
	}
	
	public PolynomVarInfo(Variable v, int deg) {
		init(deg, v, new PolynomDegrees());
	}
	public PolynomVarInfo(Variable v) {
		init(0, v, new PolynomDegrees());
	}
	public PolynomVarInfo() {
		init(0, null, new PolynomDegrees());
	}
	public int getMaxDegree() {
		return maxDegree;
	}
	
	///was once public, now private to prevent errors.
	private void setMaxDegree(int degP) {
		maxDegree = degP;
	}
	
	public void updateMaxDegree(int prevMax) {
		int i = prevMax;
		while (!degrees.getDegree(i)) i--;
		setMaxDegree(i);
	}
	
	public boolean getDegreeExists(int deg) {
		return degrees.getDegree(deg);
	}
	public void setDegreeExists(int deg, boolean degExists) {
		if (degExists && deg > maxDegree) setMaxDegree(deg);
		degrees.setDegree(deg, degExists);
		///if (!degExists && deg == maxDegree) updateMaxDegree(deg);
	}
	
	public Variable getVariable() {
		return v;
	}
	
	public PolynomDegrees getDegsCopy() {
		return degrees.copy(maxDegree);
	}
	
	public boolean[] getAllDegrees() {
		boolean[] buff = new boolean[maxDegree+1];
		for (int i = 0; i < maxDegree+1; i++) {
			buff[i] = getDegreeExists(i);
		}
		return buff;
	}
	
	///Return all degrees that mathces a certain value (exists, dont exists)
	public List<Integer> matchingDegs(boolean exists) {
		LinkedList<Integer> matches = new LinkedList<Integer>();
		for (int i = 1; i <= maxDegree; i++) {
			if (getDegreeExists(i) == exists) matches.add(i);
		}
		return matches;
	}
}

///Takes a list of polynom var infos and returns a polynomvarinfo in which if and only if degree i exists
///in any of the infos of the list, than it exists in the returned info.
class VarPolynomMixer {
	private PolynomInfo ret;
	
	public VarPolynomMixer() {
		ret = PolynomInfo.emptyPolynom();
	}
	
	private void mixDegrees(PolynomVarInfo mixing) {
		PolynomVarInfo retVar = ret.getInfo(mixing.getVariable());
		for (int i = 1; i <= mixing.getMaxDegree(); i++) {
			if (mixing.getDegreeExists(i)) {
				retVar.setDegreeExists(i, true);
			}
		}
		ret.setInfo(retVar);
	}
	
	public PolynomInfo getInfo() {
		return ret;
	}
	
	public void mixInfo(PolynomVarInfo newVar) {
		mixDegrees(newVar);
	}
	public void mixZeroDeg(boolean zeroDeg) {
		if (zeroDeg) ret.setZeroDeg(true);
	}
	public void mixZeroDeg(PolynomInfo mixedInf) {
		mixZeroDeg(mixedInf.getZeroDeg());
	}
}

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

class AdditionPolynomIdentifier extends PolynomIdentifier {
	public AdditionPolynomIdentifier(Symbol sP) {
		super(sP);
	}
	
	@Override
	public PolynomInfo isPolynom() {
		List<PolynomInfo> childInfos = getChildrenPolynomInfos();
		PolynomMixer listExtendor = new PolynomMixer(childInfos);
		
		if(!isEveryChildPolynom(childInfos)) registerNotPolynom();
		else inf = listExtendor.mixPolynoms();
		
 		return inf;
	}
 
}

class MultiPolynomIdentifier extends PolynomIdentifier {
	public MultiPolynomIdentifier(Symbol sP) {
		super (sP);
	}
	
	@Override
	public PolynomInfo isPolynom() {
		List<PolynomInfo> childInfos = getChildrenPolynomInfos();
		PolynomInfoListMultiply adder = new PolynomInfoListMultiply(childInfos);
		
		if (!isEveryChildPolynom(childInfos)) registerNotPolynom();
		else inf = adder.add();
		return inf;
	}
}

class VarPolynomIdentifier extends PolynomIdentifier {
	public VarPolynomIdentifier(Symbol sP) {
		super (sP);
	}
	
	private PolynomVarInfo getFirstDegreeVar() {
		 Variable var = Variable.copy(s);
		 PolynomVarInfo varInf = new PolynomVarInfo(var, 1);
		 varInf.setDegreeExists(1, true);
		 return varInf;
	}
	
	private void putSingleVarInfo() {
		 inf.makeEmptyPolynom();
		 inf.setInfo(getFirstDegreeVar());
	}
	
	@Override
	public PolynomInfo isPolynom() {
		putSingleVarInfo();
		return inf;
	}
	
}

class PowPolynomIdentifier extends PolynomIdentifier {
	private Symbol parameter(int index) {
		return s.getParams().get(index);
	}
	
	private Symbol getBase() {
		return parameter(0);
	}
	
	private Symbol getExponent() {
		return parameter(1);
	}
	
	public PowPolynomIdentifier(Symbol sP) {
		super (sP);
	}
	
	private boolean validPolynom(PolynomInfo baseInfo, NumberInfo expoInfo) {
		if (!baseInfo.getIsPolynom() || !expoInfo.getIsNum()) return false;
		else return true;
	}
	
	/*(private void mixAllUpperDegrees(PolynomVarInfo out, PolynomVarInfo baseInf, int multiplier, int degree) {
		for (int i = degree; i <= baseInf.getMaxDegree(); i++) {
			if (baseInf.getDegreeExists(i)) out.setDegreeExists( degree, degExists);
		}
	}*/
	
	private PolynomInfo getOnePolynomInfo() {
		PolynomInfo buff = new PolynomInfo();
		buff.setZeroDeg(true);
		return buff;
	}
	
	private PolynomInfo multiplyInfo(PolynomInfo baseVar, int exponent) {
		PolynomInfo total = getOnePolynomInfo();
		PolynomInfoAdder adder = new PolynomInfoAdder(total, baseVar);
		for (int i = 0; i < exponent; i++) {
			adder.setAdded(total, baseVar);
			total = adder.add();
		}
		return total;
	}
	
	/*private void updateVarInfo(PolynomInfo out, PolynomVarInfo baseVar, int multiplier) {
		PolynomVarInfo varInf = new PolynomVarInfo(baseVar.getVariable(), baseVar.getMaxDegree() * multiplier);
		for (int i = 0; i <= baseVar.getMaxDegree(); i++) {
			if (baseVar.getDegreeExists(i)) {
				multiplyAllInfos(varInf, baseVar, multiplier, i);
			}
		}
		out.setInfo(varInf);
	}*/
	
	/*private void updatePolynomInfo(PolynomInfo out, PolynomInfo baseInf, NumberInfo expoInf) {
		int count = baseInf.varCount();
		int multi = expoInf.getNum().getInt();
		for (int i = 0; i < count; i++) {
			updateVarInfo(out, baseInf.getInfo(i), multi);
		}
	}*/
	
	///TODO Fix it, not good... specifically updatePolynomInfo
	@Override
	public PolynomInfo isPolynom() {
		PolynomInfo baseInf = new PolynomInfo();
		NumberInfo expoInf = new NumberInfo();
		Symbol base, expo;
		base = getBase();
		expo = getExponent();
		baseInf = base.getIdentifier().isPolynom();
		expoInf = expo.getIdentifier().isRawNumber();
		
		if (!validPolynom(baseInf, expoInf)) registerNotPolynom();
		else {
			inf = multiplyInfo(baseInf, (int) expoInf.getNum().getDouble()); ///TODO What if exponent is not an integer?
		}
		return inf;
	}
}

class NumberPolynomIdentifier extends PolynomIdentifier {
	public NumberPolynomIdentifier(Symbol s) {
		super(s);
	}
	
	@Override
	public PolynomInfo isPolynom() {
		inf.makeEmptyPolynom();
		inf.setZeroDeg(true);
		return inf;
	}
}

///TODO:
///Add isTranslatedNumber();
abstract class NumberIdentifier {
	public abstract NumberInfo isRawNumber();
}



class NumberInfo {
	private boolean isNum;
	private CalcNumber num;
	
	public static NumberInfo notNumber() {
		return new NumberInfo(null, false);
	}
	
	public NumberInfo(CalcNumber cn, boolean isNumP) {
		num = cn;
		isNum = isNumP;
	}
	public NumberInfo(CalcNumber cn) {
		num = cn;
		isNum = true;
	}
	public NumberInfo() {
		isNum = false;
		num = null;
	}
	public CalcNumber getNum() {
		return num;
	}
	
	public void setNum(CalcNumber numP) {
		num = numP;
	}
	
	public boolean getIsNum() {
		return isNum;
	}
	public void setIsNum(boolean isP) {
		isNum = isP;
	}
}
