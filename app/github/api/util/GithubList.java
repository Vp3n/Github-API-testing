package github.api.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * 
 * Decorator of a java List, allow to easily add functionality 
 * or alter defaults behaviors and give subclass an free iterable
 * using composition over inheritance
 * 
 * @author Vivian Pennel
 * @param <E> The final type used by the generic list
 */
public class GithubList<E> implements List<E> {
	
	private final List list = new ArrayList<E>();

	public void add(int index, E element) {
		list.add(index, element);
	}

	public boolean add(E e) {
		return list.add(e);
	}

	public boolean addAll(Collection c) {
		return list.addAll(c);
	}

	public boolean addAll(int index, Collection c) {
		return list.addAll(index, c);
	}

	public void clear() {
		list.clear();
	}

	public boolean contains(Object o) {
		return list.contains(o);
	}

	public boolean containsAll(Collection c) {
		return list.containsAll(c);
	}

	public boolean equals(Object o) {
		return list.equals(o);
	}

	public E get(int index) {
		return (E) list.get(index);
	}

	public int hashCode() {
		return list.hashCode();
	}

	public int indexOf(Object o) {
		return list.indexOf(o);
	}

	public boolean isEmpty() {
		return list.isEmpty();
	}

	public Iterator iterator() {
		return list.iterator();
	}

	public int lastIndexOf(Object o) {
		return list.lastIndexOf(o);
	}

	public ListIterator listIterator() {
		return list.listIterator();
	}

	public ListIterator listIterator(int index) {
		return list.listIterator(index);
	}

	public E remove(int index) {
		return (E) list.remove(index);
	}

	public boolean remove(Object o) {
		return list.remove(o);
	}

	public boolean removeAll(Collection c) {
		return list.removeAll(c);
	}

	public boolean retainAll(Collection c) {
		return list.retainAll(c);
	}

	public E set(int index, E element) {
		return (E) list.set(index, element);
	}

	public int size() {
		return list.size();
	}

	public List subList(int fromIndex, int toIndex) {
		return list.subList(fromIndex, toIndex);
	}

	public Object[] toArray() {
		return list.toArray();
	}

	public Object[] toArray(Object[] a) {
		return list.toArray(a);
	}

}
