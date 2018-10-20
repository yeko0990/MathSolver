package yekocalc;

///an ID in a symbol- symbols may or may not keep a calc number id.
public abstract class SymbolID {
	public abstract CalcNumber Id();
	public abstract void setId(CalcNumber newId);
	public abstract int idHash();
}

class CalcNumberID extends SymbolID {
	protected CalcNumber id;
	
	public CalcNumberID() {
		id = null;
	}
	
	public CalcNumberID(CalcNumber num) {
		id = num;
	}
	
	@Override
	public CalcNumber Id() {
		return id;
	}
	
	@Override
	public void setId(CalcNumber newId) {
		id = newId;
	}
	
	@Override
	public int idHash() {
		return id.hashCode();
	}
}

class ConstantCalcNumberID extends CalcNumberID{
	private boolean isSet;
	public ConstantCalcNumberID() {
		
	}
	
	public ConstantCalcNumberID(CalcNumber idP) {
		super(idP);
		isSet = true;
	}
	
	@Override
	public CalcNumber Id() {
		return id;
	}
	
	@Override
	public void setId(CalcNumber newId) {
		if (isSet) throw new IllegalArgumentException();
		if (newId == null) return;
		id = newId;
		isSet = true;
	}
}

class NoId extends SymbolID {
	@Override
	public CalcNumber Id() {
		return null;
	}
	
	@Override
	public void setId(CalcNumber newId) {
		return;
	}
	
	@Override
	public int idHash() {
		return 0;
	}
}
