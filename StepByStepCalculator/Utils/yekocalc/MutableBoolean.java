package yekocalc;

public class MutableBoolean {
	public final static MutableBoolean NULL = new MutableBoolean(); ///To be used when you don't want to pass
																   	///a mutableBoolean as an argument to a function.
	
	boolean val;
	public MutableBoolean(boolean v) {
		val = v;
	}
	public MutableBoolean() {
		this(false);
	}
	
	public void set(boolean newVal) {
		val = newVal;
	}
	public boolean value() {
		return val;
	}
}
