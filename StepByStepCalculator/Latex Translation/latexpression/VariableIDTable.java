package latexpression;

import yekocalc.CalcNumber;

public interface VariableIDTable {
	public CalcNumber getID(String name);
	public String getName(CalcNumber id);
}
