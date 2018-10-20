package latexpression;
import java.util.*;

import yekocalc.CalcNumber;

///Tracks IDs for variable names. Adds new ID for a new name that is queried.
public class VariableIDDynamicTable implements VariableIDTable {
	private CalcNumber nextID;
	private final CalcNumber nextIDJump;
	HashMap<CalcNumber, String> nameTable;
	HashMap<String, CalcNumber> idTable;
	
	public VariableIDDynamicTable() {
		nameTable = new HashMap<CalcNumber, String>();
		idTable = new HashMap<String, CalcNumber>();
		nextID = new CalcNumber(1);
		nextIDJump = new CalcNumber(1);
	}

	private void registerPair(CalcNumber id, String name) {
		nameTable.put(id, name);
		idTable.put(name, id);
	}
	
	private void addName(String name) {
		registerPair(nextID, name);
		nextID = nextID.add(nextIDJump);
	}
	
	@Override
	public CalcNumber getID(String name) {
		if (!idTable.containsKey(name)) addName(name);
		return idTable.get(name);
	}

	@Override
	public String getName(CalcNumber id) {
		return nameTable.containsKey(id) ? nameTable.get(id) : null; 
	}

}
