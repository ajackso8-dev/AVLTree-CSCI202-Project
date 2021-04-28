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
	 * @param <T> generic type
	 */
	private class BinaryTreeNode<T> {
		/* instance variables */
		private T data;
		private BinaryTreeNode<T> parent;
		private BinaryTreeNode<T> left;
		private BinaryTreeNode<T> right;
		
		
		/* constructors */
		public BinaryTreeNode(T data) {
			this.data = data;
			this.parent = null;
			this.left = null;
			this.right = null;
		}
		
		public T getData() {
			return data;
		}
		
		
		public void setData(T data) {
			this.data = data;
		}
		
		
		public BinaryTreeNode<T> getParent() {
			return parent;
		}
		
		
		public void setParent(BinaryTreeNode<T> parent) {
			this.parent = parent;
		}
		
		
		public BinaryTreeNode<T> getLeft() {
			return left;
		}
		
		
		public void setLeft(BinaryTreeNode<T> left) {
			this.left = left;
		}
		
		
		public BinaryTreeNode<T> getRight() {
			return right;
		}
		
		
		public void setRight(BinaryTreeNode<T> right) {
			this.right = right;
		}
		
		/**
		 * Returns the number of children a node has.
		 * @return the number of children a node has.
		 */
		public int numChildren() {
			int total = 0;
			// recurse left
			if(this.left != null) {
				total = 1 + left.numChildren();
			}
			// recurse right
			if(this.right != null) {
				total = total + 1 + right.numChildren();
			}
			return total;
		}
	}
	
	// instance variables
	private BinaryTreeNode<T> root;
	private int size;
	
	// constructors -------------------
	public LinkedBST(T data) {
		this.root = new BinaryTreeNode<T>(data);
	}
	
	
	public LinkedBST(T data, BinaryTreeNode<T> left, BinaryTreeNode<T> right) {
		this.root = new BinaryTreeNode<T>(data);
		if(left != null) {
			this.root.setLeft(left);
		}
		if(right != null) {
			this.root.setRight(right);
		}
	}
	
	
	public LinkedBST() {
		this.root = null;
	}
	// -----------------------------------
	
	@Override
	public T getRootElement() {
		if(root == null) {
			return null;
		}
		return root.getData();
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
	private void traverseInOrder(BinaryTreeNode<T> node, LinkedList<T> list) {
		// check base case
		if(node != null) {
			this.traverseInOrder(node.left, list); // recurse left
			list.add(node.getData()); // visit node
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
	private void traversePreOrder(BinaryTreeNode<T> node, LinkedList<T> list) {
		// check base case
		if(node != null) {
			list.add(node.getData()); // visit node
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
	private void traversePostOrder(BinaryTreeNode<T> node, LinkedList<T> list) {
		// check base case
		if(node != null) {
			this.traverseInOrder(node.left, list); // recurse left
			this.traverseInOrder(node.right, list); // recurse right
			list.add(node.getData()); // visit node
		}
	}

	
	@Override
	public Iterator<T> iteratorLevelOrder() {
		LinkedList<T> list = new LinkedList<T>();
		Queue<BinaryTreeNode<T>> work = new ArrayDeque<BinaryTreeNode<T>>();
		work.add(this.root); // start off with the root node.
		while(!(work.isEmpty())) {
			BinaryTreeNode<T> node = work.remove(); // pop the first.
			// process this node
			list.add(node.getData());
			// enqueue the children on this node.
			if(node.getLeft() != null) {
				work.add(node.getLeft());
			}
			if(node.getRight() != null) {
				work.add(node.getRight());
			}
		}
		return list.iterator();
	}

	
	@Override
	public int insert(T element) {
		size++;
		int numOfEdgesFollwed = 0;
		BinaryTreeNode<T> node = new BinaryTreeNode<T>(element); // new node to implement.
		BinaryTreeNode<T> var = this.root; // loop variable.
		BinaryTreeNode<T> parent = this.root; // trailing parent node.
		while(var != null) {
			parent = var; // set trailing parent;
			if(node.getData().compareTo(var.getData()) > 0) {
				var = var.right; // move to right subtree if node > var.
				numOfEdgesFollwed++;
			} else {
				var = var.left; // move to left subtree if node < var.
				numOfEdgesFollwed++;
			}
		}
		if(parent == null) { // tree was empty.
			this.root = node; // set as new root node.
		} else if(node.getData().compareTo(parent.getData()) >= 0) {
			parent.right = node; // set as parent's right child if node >= parent.
		} else {
			parent.left = node; // set as parent's left child otherwise.
		}
		return numOfEdgesFollwed;
	}
	
	/**
	 * Replaces an existing node with another, while maintaining sub-trees.
	 * @param u node to replace.
	 * @param v node to replace with.
	 */
	private void transplant(BinaryTreeNode<T> u, BinaryTreeNode<T> v) {
		if(u.getParent() == null) {
			this.root = v; // set v as new root
		} else if(u == u.parent.left) {
			u.parent.left = v; // set v as u's parent's new left child.
		} else {
			u.parent.right = v; // set v as u's parent's new right child.
		}
	}

	
	@Override
	public int height() {
		BinaryTreeNode<T> node = this.root;
		int height = 1;
		while(node != null) { // loop left subtree
			height++; // increment while going through each level.
			node = node.left;
		}
		return height;
	}

	
	@Override
	public T maximum() {
		if(isEmpty()) {
			return null;
		}
		BinaryTreeNode<T> x = maximum(this.root);
		
		if(x != null) {
			return x.getData();
		} else {
			return null;
		}
	}
	
	/**
	 * Returns the maximum value in the binary tree.
	 * @param x starting node.
	 * @return the maximum value in the binary tree.
	 */
	private BinaryTreeNode<T> maximum(BinaryTreeNode<T> x) {
		while (x.getRight() != null) {
			x = x.right; // loop through right subtree.
		}
		return x;
	}

	@Override
	public T minimum() {
		if(isEmpty()) {
			return null;
		}
		BinaryTreeNode<T> x = minimum(this.root);
		
		if(x != null) {
			return x.getData();
		} else {
			return null;
		}
	}
	
	/**
	 * Returns the minimum value in the binary tree.
	 * @param x starting node.
	 * @return the minimum value in the binary tree.
	 */
	private BinaryTreeNode<T> minimum(BinaryTreeNode<T> x) {
		while (x.getLeft() != null) {
			x = x.left; // loop through left subtree.
		}
		return x;
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
	private String print(BinaryTreeNode<T> node, int level) {
		String ret = "";
		if(node != null) {
			for(int i = 0; i < level; i++) { // indent based on level.
				ret += "\t";
			}
			// add in this node's data.
			ret += node.getData(); // toString on the data;
			ret += "\n"; // return to next line.
			ret += this.print(node.getRight(), level + 1); // recurse right
			ret += this.print(node.getLeft(), level + 1); // recurse left
		}
		return ret;
	}

}
