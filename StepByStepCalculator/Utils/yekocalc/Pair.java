package yekocalc;

public class Pair<T> {
	public T o1, o2;
	
	public Pair(T o1, T o2) {
		this.o1 = o1;
		this.o2 = o2;
	}
	
	public T get1() {
		return o1;
	}
	public T get2() {
		return o2;
	}
	public void set1(T new1) {
		this.o1 = new1;
	}
	public void set2(T new2) {
		this.o2 = new2;
	}

}
