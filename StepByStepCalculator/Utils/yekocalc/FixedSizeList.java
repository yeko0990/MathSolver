package yekocalc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class FixedSizeList<E> extends ArrayList<E> {
	private final int fixedSize;
	
	private void expandListToSize() {
		if (fixedSize < 0) throw new IllegalArgumentException();
		while (size() != fixedSize) add(null);
	}
	
	public FixedSizeList(int listSize) {
		fixedSize = listSize;
		expandListToSize();
	}

	@Override
	public boolean add(E e) {
		if (size() != fixedSize) super.add(e);
		else throw new UnsupportedOperationException();
		return true;
	}

	@Override
	public void add(int index, E element) {
		throw new UnsupportedOperationException();

	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}
	
	///TODO Maybe allow iterators- check about it.
	@Override
	public Iterator<E> iterator() {
		return super.iterator(); ///TODO Iterator is dangerous!!! - may remove elements...
								 ///	 SHOULD CREATE A NON-CHANGING ITERATOR CLASS
	}

	@Override
	public ListIterator<E> listIterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean remove(Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public E remove(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		throw new UnsupportedOperationException();
	}
}
