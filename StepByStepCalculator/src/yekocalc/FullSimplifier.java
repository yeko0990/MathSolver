package yekocalc;
import java.util.*;

///Responsible for the simplification of the Symbol WITHOUT pre-simplification of its parameters.
interface DirectSimplifier {
	///public boolean simplify(Symbol s); ///Simplifies the symbol, returns true if took any action
	public boolean isLightSimplification(); ///A LIGHT SIMPLIFICATION is a simplification who is not shown as a step.
											///Classic examples: 3*1 -> 3
											///Useful, for example,  when opening expressions: 3(x+2) -> 3x + 6
											///													        (not 3x + 3*2)
	public boolean simplifyNext(); 		   ///Takes next step in simplifying the symbol, returns true
										   ///if took any action.
}

class DirectSimplifierSet  {
	private List<DirectSimplifier> simplifiers;
	
	public DirectSimplifierSet(List<DirectSimplifier> simps) {
		simplifiers = simps;
	}
	
	public DirectSimplifierSet(DirectSimplifier...simps) {
		this(new LinkedList<DirectSimplifier>(Arrays.asList(simps)));
	}
	
	public boolean simplifyNext(boolean onlyLight) {
		DirectSimplifier nextSimplifier;
		for (int i = 0; i < simplifiers.size(); i++) {
			nextSimplifier = simplifiers.get(i);
			if (onlyLight && !nextSimplifier.isLightSimplification()) continue;
			if (simplifiers.get(i).simplifyNext()) return true;
		}
		return false;
	}
	
	/*public boolean simplify(Symbol s) {
		boolean did = false;
		for (int i = 0; i < simplifiers.size(); i++) {
			did = simplifiers.get(i).simplify(s) || did;
		}
		return did;
	}*/
}


///Responsible for the simplification of symbols parameters,
///while delegating the actual simplification of a symbol to a DirectSimplifier
class FullSimplifier {
	protected Expression exp; //Symbol to be simplified
	protected List<Symbol> params; ///symbol 'exp' parameters
	///protected int nextParam; ///Simplifier state- if simplifying continiuously (in steps), we need to keep 
							 ///current parameter that is simplified next.
	protected DirectSimplifierSet simplifier;
	
	///Also standarizes the symbol, so it manipulating it properly will be possible
	private void prepareVars() {
		exp.standarize();
		params = exp.getStoredSym().getParams();
	}
	
	public FullSimplifier(Symbol sP, DirectSimplifierSet simpP) {
		exp = new Expression(sP);
		//params = s.getParams();
		///nextParam = 0;
		simplifier = simpP;
	}
	
	///Returns false if all params are simplified
	private boolean simplifyNextParam(boolean onlyLight) {
		/*while (nextParam < params.size()) {
			if (params.get(nextParam).symSimplifier().simplifyNext()) return true;
			else nextParam++;
		}*/
		if (params == null) return false;
		boolean nextAction;
		Expression nextChild;
		for (int i = 0; i < params.size(); i++) {
			nextChild = new Expression(params.get(i));
			if (onlyLight) nextAction = nextChild.lightSimplifyNext();
			else nextAction = nextChild.simplifyNext();
			if (nextAction) {
				exp.standarize();
				return true;
			}
		}
		return false;
	}
	
	protected boolean simplifyThis(boolean onlyLight) {
		return simplifier.simplifyNext(onlyLight);
	}
	
	///TODO make sure the symbol is standarized after EVERY step. (looks done...)
	public Symbol simplifyNext(MutableBoolean didAction, boolean onlyLight) {	///If there is unsimplified parameter- simplify it and return true
									///else try to simplify symbol s and return true only if an action was
									///taken.
		prepareVars();
		if (simplifyNextParam(onlyLight)) { 
			prepareVars();
			didAction.set(true);
		}
		else if (simplifyThis(onlyLight)) {
			prepareVars();
			didAction.set(true);
		}
		else didAction.set(false);
		return exp.getStoredSym();
	}
	
	private boolean simplifyAllChilds(boolean onlyLight) {
		boolean isChanged = false;
		while (simplifyNextParam(onlyLight)) isChanged = true; 
		return isChanged;
	}
	
	
	///TODO Needs optimizing- standarization is called for numerous times in a row at the end of
	///		simplification because end of simplify is standarizing before return and it is called recursively. 
	private boolean simplifyRepetition(boolean onlyLight) {
		boolean childSimp, thisSimp;
		childSimp = simplifyAllChilds(onlyLight);
		prepareVars();
		thisSimp = simplifyThis(onlyLight);
		if (thisSimp) {		///If simplified the current symbol, repeat the simplification of childs
							///and the simplification of the symbol.
			prepareVars();
			if (onlyLight) exp.setStoredSym(exp.getStoredSym().lightSimplifiedForm(new MutableBoolean()));
			else exp.setStoredSym(exp.getStoredSym().simplifiedForm(new MutableBoolean()));		 
		}
		return (childSimp || thisSimp);
	}
	
	public final Symbol simplify(MutableBoolean didAction, boolean onlyLight) {
		prepareVars();
		didAction.set(simplifyRepetition(onlyLight));
		prepareVars();
		return exp.getStoredSym();
		/*boolean acted = false;
		didAction.set(false);
		do {
			exp.setStoredSym(this.simplifyNext(didAction));
			acted = didAction.value() || acted;
		} while (didAction.value());
		didAction.set(acted);
		return exp.getStoredSym();*/
	}
}