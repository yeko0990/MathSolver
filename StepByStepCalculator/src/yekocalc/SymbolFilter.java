package yekocalc;
 
public interface SymbolFilter<T> {
	public T filter(Symbol target);
	public boolean isMatching(T info);
}
