package utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.function.Consumer;

//$Id$

/**
 * A list that will iterate through a array of lists as if its a single list.
 * */
public class UnModifiableTwoDList<T> implements List<T>{
	
	List<T>[] arr;
	int size = -1;
	
	public UnModifiableTwoDList(List<T>[] arr){
		this.arr  = arr;
	}

	/**
	 * A forEach implementation for private usage, that will not create an iterator instance.
	 * */
	private void loop(Consumer<T> action) {
		int arr_idx = -1;
		int list_idx = 0;
		Objects.requireNonNull(action);
		while( ( ++arr_idx < this.arr[list_idx].size() ) || ( ((arr_idx=0)|++list_idx) < this.arr.length ) ){
			action.accept(this.arr[list_idx].get(arr_idx));
		}
	}
	
	@Override
	public int size() {
		if(this.size < 0){
			this.size++;
			this.loop((T obj)->{	
				this.size++; 
			});
		}
		return this.size;
	}

	@Override
	public boolean isEmpty() {
		return (this.size()==0);
	}
	
	@Override
	public int indexOf(Object o) {
		//cannot use the loop as exiting the loop in the middle is not possible.
		int ctr = -1;
		int arr_idx = -1;
		int list_idx = 0;
		while( ( ++arr_idx < this.arr[list_idx].size() ) || ( ((arr_idx=0)|++list_idx) < this.arr.length ) ){
			ctr++;
			if(this.arr[list_idx].get(arr_idx).equals(o)){
				return ctr;
			}
		}
		return ctr;
	}

	@Override
	public int lastIndexOf(Object o) {
		return this.indexOf(o);		//there will be no duplicates in the field list.
	}
	
	@Override
	public boolean contains(Object o) {
		return (this.indexOf(o) > -1);
	}
	
	@Override
	public boolean containsAll(Collection<?> c) {
		boolean containsAll = false;
		for(Object o : c){
			if(!this.contains(o)){
				containsAll = false;
				break;
			}
		}
		return containsAll;
	}
	
	/**
	 * NOTE :- This method breaks the basic functionality of having O(1) complexity of retrieval in an array.
	 * 		   Never use this method to iterate over a list.
	 * */
	@Override
	@Deprecated
	public T get(int index) {
		if(index >= this.size()){
			throw new ArrayIndexOutOfBoundsException(index);
		}
		int list_idx = -1;
		int cntr = (this.arr[++list_idx].size()-1) ;
		while(cntr < index){
			cntr += this.arr[++list_idx].size() ;
		}
		cntr -= this.arr[list_idx].size();
		Object o = null;
		int idx = 0;
		while(cntr++<index){
			o = this.arr[list_idx].get(idx++);
		}
		return (T)o;
	}
	
	@Override
	public Iterator<T> iterator() {
		return new UnModifiableTwoDIterator();
	}
	
	public class UnModifiableTwoDIterator<T> implements Iterator<T>{

		int list_idx = 0;
		int arr_idx = -1;
		
		public UnModifiableTwoDIterator() {
			
		}
		
		@Override
		public boolean hasNext() {
//			System.out.println(CustList.this.arr[list_idx]);
//			if(++arr_idx < CustList.this.arr[list_idx].size()){
//				return true;
//			}else if(++list_idx < CustList.this.arr.length){
//				arr_idx = -1;
//				return true;
//			}
//			return false;
			return ( ++arr_idx < UnModifiableTwoDList.this.arr[list_idx].size() )			// if there are more elements to iterate in the list that is currently being iterated. 
						||
						( ((arr_idx=0)|++list_idx) < UnModifiableTwoDList.this.arr.length ) ;			// if there are more lists to iterate on, and set the value of arr_idx to 0.
		}

		@Override
		public T next() {
			return (T) UnModifiableTwoDList.this.arr[list_idx].get(arr_idx);
		}
		
	}

	@Override
	public Object[] toArray() {
		Object[] arr = new Object[this.size()];
		int[] idx = {0};
		this.loop((T o)->{
			arr[idx[0]++] = o;
		});
		return arr;
	}

	@Override
	public <T> T[] toArray(T[] a) {
//		int[] idx = {0};
//		this.loop((T o)->{
//			a[idx[0]++] = o;
//		});
//		return a;
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean add(T e) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean remove(Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
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
	public void clear() {
		throw new UnsupportedOperationException();		
	}

	@Override
	public T set(int index, T element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void add(int index, T element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public T remove(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListIterator<T> listIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static void main(String[] args) {
		List<String> a1 = new ArrayList<String>();
		a1.add("a");
		a1.add("b");
		a1.add("c");
		a1.add("d");
		a1.add("e");
		List<String> a2 = new ArrayList<String>();
		a2.add("f");
		a2.add("g");
		a2.add("h");
		a2.add("i");
		List<String> a3 = new ArrayList<String>();
		a3.add("j");
		a3.add("k");
		a3.add("l");
		
		List<String>[] arr = new ArrayList[3];
		arr[0] = a1;
		arr[1] = a2;
		arr[2] = a3;
		
		UnModifiableTwoDList<String> cl = new UnModifiableTwoDList(arr);
		
		for(String s : cl){
			System.out.println(s);
		}
		System.out.println("---------");
		System.out.println(cl.size());
		for(int i = 0; i<cl.size(); i++){
			System.out.println(cl.get(i));
		}
		
	}
	
	public interface BreakableConsumer<T> extends Consumer<T>{

		default boolean acceptOrReject(T t){
			this.accept(t);
			return true;
		}
		
	}

}
