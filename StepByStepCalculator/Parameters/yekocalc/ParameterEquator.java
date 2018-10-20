package yekocalc;
import java.util.*;

///Determines whether two list<symbol> objects are equal
public abstract class ParameterEquator {
	
	public final boolean equals(List<Symbol> params1, List<Symbol> params2) {
		if (params1 == null || params2 == null) return params1 == null && params2 == null;
		else return internalEquals(params1, params2);
	}
	
	///Lists are guaranteed to be non-null
	protected abstract boolean internalEquals(List<Symbol> params1, List<Symbol> params2);

	public abstract int parametersHashCode(List<Symbol> params);
}
