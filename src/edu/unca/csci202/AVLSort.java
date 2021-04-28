package edu.unca.csci202;

import java.util.Iterator;
import java.util.LinkedList;

public class AVLSort<T extends Comparable<T>> extends SortingAlgorithm<T> {
	private AVLTree<T> tree;
	private LinkedList<T> list;
	private boolean debug;
	private int numberOfEdgesFollowed;

	
	public AVLSort() {
		this.list = new LinkedList<T>();
		this.tree = new AVLTree<T>();
		this.debug = false;
		this.numberOfEdgesFollowed=0;
	}
	public AVLSort(boolean debug) {
		this.list = new LinkedList<T>();
		this.tree = new AVLTree<T>();
		this.debug = debug;
		this.numberOfEdgesFollowed=0;
	}
	
	public BinarySearchTreeADT<T> getTree() {
		return this.tree;
	}
	public void addToBack(T element) {
		this.list.add(element);
	}

	public void sort(){
		Iterator<T> itr = this.list.iterator();
		while(itr.hasNext()) {
			this.numberOfEdgesFollowed += this.tree.insert(itr.next());
		}
		
	}
	
	public void clear() {
		this.list.clear();
		this.tree = new AVLTree<T>();
		this.numberOfEdgesFollowed=0;
	}
	
	public void printStats() {
		System.out.print("numberOfEdgesFollowed="+this.numberOfEdgesFollowed);
		System.out.print("  size="+this.tree.size());
		System.out.println("  heightOfTree="+this.tree.height());
		if(this.debug) System.out.println(this.tree);
	}

	public String toString() {
		String ret="[";
		Iterator<T> itr = this.tree.iteratorInOrder();
		while(itr.hasNext()) {
			ret += itr.next() + ", ";
		}
		return ret + "]";
	}


}
