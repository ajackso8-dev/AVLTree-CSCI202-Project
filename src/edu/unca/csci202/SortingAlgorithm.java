package edu.unca.csci202;


public abstract class SortingAlgorithm<T extends Comparable<T>> {

	protected class Node<T>{
		Node<T> next;
		Node<T> prev;
		T data;
	}
	protected Node<T> head;
	protected Node<T> tail;
	protected boolean debug;
	protected long numberOfComparisons;
	protected long numberOfSwaps;

	public SortingAlgorithm() {
		this.numberOfComparisons = 0;
		this.numberOfSwaps = 0;
		this.debug = false;
	}
	public SortingAlgorithm(boolean debug) {
		this.numberOfComparisons = 0;
		this.numberOfSwaps = 0;
		this.debug = debug;
	}
		
	public abstract void sort();
	
	public void printStats() {
		System.out.print("#numberOfSwaps: "+numberOfSwaps+"  ");
		System.out.println("#numberOfComparisons: "+numberOfComparisons);
	}

	
	protected void swap(Node<T> a, Node<T> b) {
		T tmp = b.data;
		b.data = a.data;
		a.data = tmp;
		this.numberOfSwaps++;
	}
	
	public void clear() {
		this.numberOfComparisons = 0;
		this.numberOfSwaps = 0;
		this.head = null;
		this.tail = null;
	}

	public void addToBack(T element) {
		Node<T> node = new Node<T>();
		node.data = element;
		if(head == null) { 
			// empty list
			this.head = node;
			this.tail = node;
			node.next = null;
			node.prev = null;
		}else {
			this.tail.next = node;
			node.prev = this.tail;
			node.next = null;
			this.tail = node;
		}
	}
	
	public String toString() {
		return this.getSubString(this.head, this.tail);
	}
	
	public String getSubString(Node<T> begin, Node<T> end) {
		String ret = "[";
		Node<T> current = begin;
		while(current != null) {
			ret += current.data;
			if(current.next != null) {
				ret += ",";
			}
			if(current == end) {break;}
			current = current.next;
		}
		return ret + "]";
	}
}
