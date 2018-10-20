package yekocalc;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class SortPolynomSymbol implements SymbolVisitor {
	private Variable var;
	
	public SortPolynomSymbol(Variable var) {
		this.var = var;
	}

	@Override
	public void visit(NumberSym sym) {
		return;
	}

	@Override
	public void visit(Variable sym) {
		return;
	}

	private boolean isPowerOfDegree(Symbol next, int i) {
		return !new ExplicitCoefficient(next, new Power(var, NumberSym.intSymbol(i))).getExplicitCoefficient().equals(NumberSym.zero);
	}
	
	@Override
	public void visit(Addition sym) {
		PolynomInfo info = sym.getIdentifier().isPolynom();
		PolynomVarInfo varInfo = info.getInfo(var);
		boolean[] degs = varInfo.getAllDegrees();
		
		List<Symbol> oldParams = sym.getParams();
		List<Symbol> newParams = new LinkedList<Symbol>();
		for (int i = degs.length-1; i > 0; i--) {
			if (!degs[i]) continue;
			Iterator<Symbol> iterate = oldParams.iterator();
			while (iterate.hasNext()) {
				Symbol next = iterate.next();
				if (isPowerOfDegree(next, i)) {
					newParams.add(next);
					iterate.remove();
				}
			}
		}
		
		for (Symbol nextOld : oldParams) {
			newParams.add(nextOld);
		}
		
		sym.setParams(newParams);
	
	}

	@Override
	public void visit(Multiplication sym) {
		return;
	}

	@Override
	public void visit(Power sym) {
		return;
	}

	@Override
	public void visit(Fraction sym) {
		sym.numerator().accept(this);
		sym.denominator().accept(this);
	}

	@Override
	public void visit(Root sym) {
		sym.base().accept(this);

	}

	@Override
	public void visit(PlusMinus sym) {
		sym.parameter().accept(this);
	}

}
