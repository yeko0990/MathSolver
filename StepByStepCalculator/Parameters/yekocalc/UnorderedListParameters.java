package yekocalc;
import java.util.*;

public class UnorderedListParameters extends Parameters {
	List<Symbol> parameters;
	
	public UnorderedListParameters() {
		super(new ParameterUnorderedEquator());
	}
	
	public UnorderedListParameters(List<Symbol> params) {
		this();
		parameters = params;
	}
	
	@Override
	public List<Symbol> getParameters() {
		return parameters;
	}
	
	@Override
	public void setParameters(List<Symbol> params) {
		parameters = params;
	}

}
