package yekocalc;

import java.util.List;
import java.util.Objects;

import latexpression.LatexExpression;
import latexpression.LatexVariable;
import latexpression.VariableIDTable;

///*DONE*
///VERY VERY IMPORTANT!!!!!!!
///TO IMPLEMENT THE equals() METHOD!!!

public class Variable extends Symbol {
	@Override
	public void accept(SymbolVisitor visitor) {
		visitor.visit(this);
	}
	///We make this field immutable so the same instance of Variable can be used in many places
	///without fear that it would be changed.
	///final private CalcNumber idval;
	public static Variable copy(Symbol vSym) {
		return new Variable(vSym.id());
	}
	
	public Variable(CalcNumber idP) {
		super();
		setId(idP);
	}
	
	@Override
	protected SymbolID createIdType() {
		return new ConstantCalcNumberID();
	}
	
	@Override
	protected Parameters createParameters() {
		return new NoParameters();
	}
	
	@Override
	protected SymbolStandarizer createStd() {
		return new NoStandarize();
	}
	@Override
	protected FullSimplifier createSimp() {
		return new FullSimplifier(this, new DirectSimplifierSet(new NoSimplify()));
	}
	@Override
	protected SymbolPatternIdentifier createId() {
		return new SymbolPatternIdentifier(
				new NotRawNumber(),
				new NotImplicatedNumber(),
				new NoTranslatedNumberIdentifier(this),
				new VarPolynomIdentifier(this)
				);
	}
	
	@Override
	public SymType getType() {
		return SymType.Variable;
	}
	
	@Override
	public boolean containsVariable(Variable v) {
		return this.equals(v);
	}
	
	@Override
	public LatexExpression latex(VariableIDTable idTable) {
		return new LatexVariable(idTable.getName(this.id()));
	}
	
	/*@Override
	public String print() {
		return "x" + this.id().toString(); ///TODO Extremely temporary printing... Change required!
	}*/
	
	@Override
	protected Symbol copySymbolType() {
		return new Variable(null);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id().getDouble());
	}
}