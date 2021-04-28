package edu.unca.csci202;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Linked Binary Search Tree.
 * @author Aaron Jackson
 *
 * @param <T> generic type
 */
public class LinkedBST<T extends Comparable<T>> implements BinarySearchTreeADT<T> {
	/**
	 * Node class for Linked Binary Search Tree Nodes.
	 * @author Aaron Jackson
	 *
	 * @param <N> generic type
	 */
	private class Node<N> {
		/* instance variables */
		private T data;
		private Node<T> parent;
		private Node<T> left;
		private Node<T> right;
		
		
		/* constructors */
		public Node(T data) {
			this.data = data;
			this.parent = null;
			this.left = null;
			this.right = null;
		}
		
		/**
		 * Returns the number of children a node has.
		 * @return the number of children a node has.
		 */
		public int numChildren() {
			int total = 0;
			if(this.left != null) {
				total = 1 + left.numChildren(); // recurse left
			}
			if(this.right != null) {
				total = total + 1 + right.numChildren(); // recurse right
			}
			return total;
		}
	}
	
	/* instance variables */
	private Node<T> root;
	private int size;
	
	/* constructors */
	public LinkedBST(T data) {
		this.root = new Node<T>(data);
	}
	
	
	public LinkedBST(T data, Node<T> left, Node<T> right) {
		this.root = new Node<T>(data);
		if(left != null) {
			this.root.left = left;
		}
		if(right != null) {
			this.root.right = right;
		}
	}
	
	
	public LinkedBST() {
		this.root = null;
	}
	
	@Override
	public T getRootElement() {
		if(root == null) {
			return null;
		}
		return root.data;
	}

	
	@Override
	public boolean isEmpty() {
		return (root == null);
	}

	
	@Override
	public Iterator<T> iterator() {
		return this.iteratorInOrder();
	}

	
	@Override
	public Iterator<T> iteratorInOrder() {
		LinkedList<T> list = new LinkedList<T>();
		this.traverseInOrder(this.root, list);
		return list.iterator();
	}
	
	/**
	 * Performs a In-Order tree traversal recursivly.
	 * @param node the starting node.
	 * @param list a LinkedList to add the nodes too.
	 */
	private void traverseInOrder(Node<T> node, LinkedList<T> list) {
		if(node != null) { // check base case
			this.traverseInOrder(node.left, list); // recurse left
			list.add(node.data); // visit node
			this.traverseInOrder(node.right, list); // recurse right
		}
	}

	
	@Override
	public Iterator<T> iteratorPreOrder() {
		LinkedList<T> list = new LinkedList<T>();
		this.traversePreOrder(this.root, list);
		return list.iterator();
	}
	
	/**
	 * Performs a Pre-Order tree traversal recursivly.
	 * @param node the starting node.
	 * @param list a LinkedList to add the nodes too.
	 */
	private void traversePreOrder(Node<T> node, LinkedList<T> list) {
		if(node != null) { // check base case
			list.add(node.data); // visit node
			this.traverseInOrder(node.left, list); // recurse left
			this.traverseInOrder(node.right, list); // recurse right
		}
	}

	
	@Override
	public Iterator<T> iteratorPostOrder() {
		LinkedList<T> list = new LinkedList<T>();
		this.traversePostOrder(this.root, list);
		return list.iterator();
	}
	
	/**
	 * Performs a Post-Order tree traversal recursivly.
	 * @param node the starting node.
	 * @param list a LinkedList to add the nodes too.
	 */
	private void traversePostOrder(Node<T> node, LinkedList<T> list) {
		if(node != null) { // check base case
			this.traverseInOrder(node.left, list); // recurse left
			this.traverseInOrder(node.right, list); // recurse right
			list.add(node.data); // visit node
		}
	}

	
	@Override
	public Iterator<T> iteratorLevelOrder() {
		LinkedList<T> list = new LinkedList<T>();
		Queue<Node<T>> work = new ArrayDeque<Node<T>>();
		work.add(this.root); // start with root node.
		while(!(work.isEmpty())) {
			Node<T> node = work.remove(); // pop first.
			list.add(node.data); // add node
			if(node.left != null) { 
				work.add(node.left); // enqueue node's children.
			}
			if(node.right != null) {
				work.add(node.right);
			}
		}
		return list.iterator();
	}

	
	@Override
	public int insert(T element) {
		size++;
		int numOfEdgesFollwed = 0;
		
		Node<T> node = new Node<T>(element);
		Node<T> var = this.root;
		Node<T> parent = this.root; // trailing parent node.
		while(var != null) {
			parent = var;
			if(node.data.compareTo(var.data) > 0) {
				var = var.right; // node > var.
				numOfEdgesFollwed++;
			} else {
				var = var.left; // node < var.
				numOfEdgesFollwed++;
			}
		}
		
		if(parent == null) { // tree was empty.
			this.root = node;
		} else if(node.data.compareTo(parent.data) >= 0) {
			parent.right = node; // node >= parent.
		} else {
			parent.left = node; // node < parent.
		}
		return numOfEdgesFollwed;
	}
	
	/**
	 * Replaces an existing node with another, while maintaining sub-trees.
	 * @param u node to replace.
	 * @param v node to replace with.
	 */
	private void transplant(Node<T> u, Node<T> v) {
		if(u.parent == null) { // u is root.
			this.root = v;
		} else if(u == u.parent.left) {
			u.parent.left = v; // v left child.
		} else {
			u.parent.right = v; // v right child.
		}
	}

	
	@Override
	public int height() {
		Node<T> node = this.root;
		int height = 1;
		while(node != null) { // loop left subtree.
			height++;
			node = node.left;
		}
		return height;
	}

	
	@Override
	public T maximum() {
		if(isEmpty()) {
			return null;
		}
		
		Node<T> node = maximum(this.root);
		
		if(node != null) {
			return node.data;
		}
		return null;
	}
	
	/**
	 * Returns the maximum value in the binary tree.
	 * @param x starting node.
	 * @return the maximum value in the binary tree.
	 */
	private Node<T> maximum(Node<T> node) {
		while (node.right != null) {
			node = node.right; // loop right subtree.
		}
		return node;
	}

	@Override
	public T minimum() {
		if(isEmpty()) {
			return null;
		}
				
		Node<T> node = minimum(this.root);
		
		if(node != null) {
			return node.data;
		}
		return null;
	}
	
	/**
	 * Returns the minimum value in the binary tree.
	 * @param x starting node.
	 * @return the minimum value in the binary tree.
	 */
	private Node<T> minimum(Node<T> node) {
		while (node.left != null) {
			node = node.left; // loop left subtree.
		}
		return node;
	}

	
	@Override
	public void delete(T element) {
		// TODO Auto-generated method stub
	}

	
	@Override
	public T find(T element) {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public boolean contains(T element) {
		// TODO Auto-generated method stub
		return false;
	}

	
	@Override
	public int size() {
		return size;
	}

	
	public String toString() {
		return print(this.root, 0);
	}
	
	/**
	 * Returns a String of the binary tree in 'tree' format.
	 * @param node starting node.
	 * @param level the level the starting node is located within.
	 * @return a String of the binary tree in 'tree' format.
	 */
	private String print(Node<T> node, int level) {
		String ret = "";
		if(node != null) {
			for(int i = 0; i < level; i++) { 
				ret += "\t"; // indent based on level.
			}
			ret += node.data; // add in this node's data.
			ret += "\n";
			ret += this.print(node.right, level + 1); // recurse right
			ret += this.print(node.left, level + 1); // recurse left
		}
		return ret;
	}

}
