package edu.unca.csci202;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * AVLTree implementation, implements BinarySearchTreeADT<T>.
 * @author Aaron Jackson
 *
 * @param <T> generic type
 */
public class AVLTree<T extends Comparable<T>> implements BinarySearchTreeADT<T> {
	
	/**
	 * AVLTree Node class that handles the behaviors and data of each node
	 * in the AVLTree.
	 * @author Aaron Jackson
	 *
	 * @param <N> generic type
	 */
	private class Node<N extends Comparable<N>> {
		private N data;
		private int height;
		private Node<N> parent;
		private Node<N> left;
		private Node<N> right;
		
		/* constructors */
		public Node (N data) {
			this.data = data;
			this.parent = null;
			this.left = null;
			this.right = null;
		}
		
		/* class methods */
		
		/**
		 * Update the value of this Node's height data field.
		 */
		public void updateHeight() {
			int leftHeight = 0;
			int rightHeight = 0;
			if(this.left != null) {
				leftHeight = this.left.height;
			}
			if(this.right != null) {
				rightHeight = this.right.height;
			}
			this.height = 1 + Math.max(leftHeight, rightHeight); 
		}
		
		/**
		 * Return the balance factor of this node as an int.
		 * @return the balance factor of this node as an int.
		 */
		public int balanceFactor() {
			int leftHeight = 0;
			int rightHeight = 0;
			if(this.left != null) {
				leftHeight = this.left.height;
			}
			if(this.right != null) {
				rightHeight = this.right.height;
			}
			return (rightHeight - leftHeight);
		}
		
	}
	
	/* instance variables */
	private Node<T> root;
	private int size;
	
	/* constructors */
	public AVLTree() {
		super();
		this.root = null; // create an empty tree.
		this.size = 0;
	}
	
	public AVLTree(T data) {
		this.root = new Node<T>(data); // new tree with only a root node.
		this.size = 1;
	}
	
	@Override
	public T getRootElement() {
		if(this.root == null) {
			return null;
		}
		return this.root.data;
	}

	
	@Override
	public boolean isEmpty() {
		return (this.root == null);
	}

	
	@Override
	public int size() {
		return this.size;
	}

	
	@Override
	public boolean contains(T targetElement) {
		T temp = this.find(targetElement);
		return temp != null;
	}

	
	@Override
	public T find(T targetElement) {
		if(!isEmpty()) {
			Node<T> node = this.find(this.root, targetElement);
			try {
				return node.data;
			} catch (NullPointerException e) {
				return null;
			}
		}
		return null;
	}

	/**
	 * private helper method that goes through each node in the AVLTree (recursivly) and
	 * compares the targetElement with the node. Returns the node equal to the target element,
	 * null otherwise.
	 * @param node node to compare targetElement with
	 * @param targetElement element to find.
	 * @return The node that's equal to target element in the AVLTree. returns null otherwise.
	 */
	private Node<T> find(Node<T> node, T targetElement) {
		if(node.data.equals(targetElement)) {
			return node; // found it, return
		}
		
		Node<T> temp;
		if(node.left != null) {
			temp = find(node.left, targetElement); // recurse left
			if(temp != null) {
				return temp;
			}
		}
		if(node.right != null) {
			temp = find(node.right, targetElement); // recurse right
			if(temp != null) {
				return temp;
			}
		}
		return null; // not found in our sub tree
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
		// check base case
		if(node != null) {
			this.traverseInOrder(node.left, list);// recurse 
			list.add(node.data); // visit node
			this.traverseInOrder(node.right, list);// recurse right
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
		// check base case
		if(node != null) {
			list.add(node.data); // visit node
			this.traverseInOrder(node.left, list); // recurse 
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
		// check base case
		if(node != null) {
			this.traverseInOrder(node.left, list); // recurse 
			this.traverseInOrder(node.right, list); // recurse right
			list.add(node.data); // visit node
		}
	}

	
	@Override
	public Iterator<T> iteratorLevelOrder() {
		LinkedList<T> list = new LinkedList<T>();
		Queue<Node<T>> work = new ArrayDeque<Node<T>>();
		work.add(this.root); // start off with the root node.
		while(!(work.isEmpty())) {
			Node<T> node = work.remove(); // pop the first.
			// process this node
			list.add(node.data);
			// enqueue the children on this node.
			if(node.left != null) {
				work.add(node.left);
			}
			if(node.right != null) {
				work.add(node.right);
			}
		}
		return list.iterator();
	}
	
	/**
	 * Insert into the AVL Tree.
	 * @param element element to insert
	 * @return number edges traversed to insert this element.
	 */
	public int insert(T element) {
		size++;
		int numOfEdgesFollwed = 0;
		
		Node<T> node = new Node<T>(element);
		Node<T> var = this.root; // loop variable.
		Node<T> parent = null; // trailing parent node.
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
		node.parent = parent;
		if(parent == null) { 
			this.root = node; // left as new root node.
		} else if(node.data.compareTo(parent.data) >= 0) {
			parent.right = node; // left as right child.
		} else {
			parent.left = node; // left as left child.
		}
		this.insertFix(node); // insertFix.
		return numOfEdgesFollwed;
	}
	
	/**
	 * Called at end of insert. Give newly inserted node as arg.
	 * @param x newly inserted node.
	 */
	private void insertFix(Node<T> x) {
		x.updateHeight(); // in-case theres' 0 iterations.
		while (x != null) {
			x.updateHeight();
			int curBal = x.balanceFactor();
			if(curBal == -2) { // -2bf
				int lBal = x.left.balanceFactor();
				if(lBal == -1 || lBal == 0) {
					this.rightRotate(x);
					x.updateHeight(); // update x.
					x.parent.updateHeight(); // update new parent.
					return;
				} else if(lBal == 1) { // left-right db
					this.leftRotate(x.left);
					this.rightRotate(x);
					x.updateHeight(); // update x
					x.parent.left.updateHeight(); // update x's siblings.
					x.parent.updateHeight();
					return;
				}
			} else if(curBal == 2) { // 2bf
				int rBal = x.right.balanceFactor();
				if(rBal == 1 || rBal == 0) {
					this.leftRotate(x);
					x.updateHeight();
					x.parent.updateHeight();
					return;
				} else if(rBal == -1) { // right-left db
					this.rightRotate(x.right);
					this.leftRotate(x);
					x.updateHeight();
					x.parent.right.updateHeight(); 
					x.parent.updateHeight();
				}
			}
			
			x = x.parent; // loop update
			
		}
	}
	
	/**
	 * Performs a  rotation on Node x in AVLTree.
	 * @param x Node to perform  rotation on.
	 */
	private void leftRotate(Node<T> node) {
		Node<T> tmp = node.right;
		node.right = tmp.left; // move tmp's  subtree to become node's right
		if(tmp.left != null) {
			tmp.left.parent = node;
		}
		
		tmp.parent = node.parent; // node's old parent becomes tmp's new parent
		if(node.parent == null) {
			this.root = tmp; // if node is root, left tmp to root.
		} else if(node == node.parent.left) {
			node.parent.left = tmp;
		} else {
			node.parent.right = tmp;
		}
		// tmp becomes parent of node, with node as the  child.
		tmp.left= node;
		node.parent = tmp;
	}
	
	/**
	 * Performs a right rotation on Node x in AVLTree.
	 * @param x Node to perform right rotation on.
	 */
	private void rightRotate(Node<T> node) {
		Node<T> tmp = node.left;
		// move tmp's right subtree to become node's 
		node.left = tmp.right;
		if(tmp.right != null) {
			tmp.right.parent = node;
		}
		// node's old parent becomes tmp's new parent
		tmp.parent = node.parent;
		if(node.parent == null) {
			this.root = tmp; // if node is root, left tmp to root.
		} else if(node == node.parent.right) {
			node.parent.right = tmp;
		} else {
			node.parent.left = tmp;
		}
		// tmp becomes parent of node, with node as the right child.
		tmp.right = node;
		node.parent = tmp;
	}
	
	/**
	 * Get height of tree in number of nodes
	 * @returns height of tree in number of nodes
	 */	
	public int height() {
		Node<T> node = this.root;
		int height = 1;
		while(node != null) { // loop  subtree
			height++; // increment while going through each level.
			node = node.left;
		}
		return height;
	}
	
	/**
	 * Return the largest element in the tree
	 * @return the largest element in the tree
	 */
	public T maximum() {
		if(isEmpty()) {
			return null;
		}
		
		Node<T> node = maximum(this.root); // find maximum.
		
		if(node != null) {
			return node.data;
		}
		return null;
	}
	
	/**
	 * Returns the maximum value in the binary tree.
	 * @param node starting node.
	 * @return the maximum value in the binary tree.
	 */
	private Node<T> maximum(Node<T> node) {
		while (node.right != null) {
			node = node.right; // loop through right subtree.
		}
		return node;
	}
	
	/**
	 * Return the smallest element in the tree
	 * @return the smallest element in the tree
	 */
	public T minimum() {
		if(isEmpty()) {
			return null;
		}
		Node<T> x = minimum(this.root);
		
		if(x != null) {
			return x.data;
		} else {
			return null;
		}
	}
	
	/**
	 * Returns the minimum value in the binary tree.
	 * @param node starting node.
	 * @return the minimum value in the binary tree.
	 */
	private Node<T> minimum(Node<T> node) {
		while (node.left != null) {
			node = node.left; // loop through  subtree.
		}
		return node;
	}
	
	
	@Override
	public void delete(T element) {
		if(contains(element)) {
			Node<T> node = this.find(this.root, element);
			delete(node);
		} else {
			System.out.println("error: element not found in tree.");
		}
	}
	
	/**
	 * Private helper method that removes a node from the AVLTree and performs deleteFix
	 * to re-balance the tree.
	 * @param node desired node to delete.
	 */
	private void delete(Node<T> node) {
		size--;
		
		boolean aBCase = true; // 3a/3b.
		
		Node<T> suc = null;
		Node<T> sucP = null;
		if(node.left == null) {
			transplant(node, node.right); // case 1
		} else if (node.right == null) {
			transplant(node, node.left); // case 2
		} else { // case 3
			suc = minimum(node.right);
			sucP = suc.parent;
			if(suc.parent != node) { // case 3b
				aBCase = false;
				transplant(suc, suc.right);
				suc.right = node.right;
				suc.right.parent = suc;
			}
			transplant(node, suc); // case 3a.
			suc.left = node.left;
			suc.left.parent = suc;
		}
		
		Node<T> lpc = checkLPC(node,suc,sucP,aBCase);
		deleteFix(lpc); 
	}
	
	/**
	 * Calculates the lowest point of change and returns the node.
	 * @param node initial node.
	 * @param suc successor of node.
	 * @param sucP successor of node's parent.
	 * @param b case check 
	 * @return the lowest point of change.
	 */
	private Node<T> checkLPC(Node<T> node, Node<T> suc, Node<T> sucP, boolean b) {
		Node<T> lpc = null;
		if(!b) {
			lpc = sucP; // lpc is suc's old parent.
		} else if(b) {
			lpc = suc; // lpc is suc
		} else if(node.parent != null) {
			lpc = node.parent; // lpc is node's parent
		}
		return lpc;
	}
	
	/**
	 * Method that re-balances the tree after removing a node.
	 * @param node the node to perform deleteFix on (perfered to be the lpc).
	 */
	private void deleteFix(Node<T> node) {
		if(node != null) { // dont run on a null-lpc.
			node.updateHeight();
			while(node != null) {
				node.updateHeight();
				int curBal = node.balanceFactor();
				if(curBal == -2) {
					int lBal = node.left.balanceFactor();
					if(lBal == -1 || lBal == 0) {
						rightRotate(node);
						node.updateHeight();
						node.parent.updateHeight();
					}
					if(lBal == 1) {
						leftRotate(node.left);
						rightRotate(node);
						node.updateHeight();
						node.parent.left.updateHeight();
						node.parent.updateHeight();
					}
				}
				if(curBal == 2) {
					int rBal = node.right.balanceFactor();
					if(rBal == 1 || rBal == 0) {
						leftRotate(node);
						node.updateHeight();
						node.parent.updateHeight();
					}
					if(rBal == -1) {
						rightRotate(node.right);
						leftRotate(node);
						node.updateHeight();
						node.parent.right.updateHeight();
						node.parent.updateHeight();
					}
				}
				
				node = node.parent;
			}
		}
	}
	
	/**
	 * Replaces an existing node with another, while maintaining sub-trees.
	 * @param u node to replace.
	 * @param v node to replace with.
	 */
	private void transplant(Node<T> u, Node<T> v) {
		if(u.parent == null) {
			this.root = v; // left v as new root
		} else if(u == u.parent.left) {
			u.parent.left = v; // left v as u's parent's new  child.
		} else {
			u.parent.right = v; // left v as u's parent's new right child.
		}
		if(v != null) {
			v.parent = u.parent; // left v's parent link.
		}
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
			for(int i = 0; i < level; i++) { // indent based on level.
				ret += "\t";
			}
			// add in this node's data.
			ret += node.data; // toString on the data;
			ret += "\n"; // return to next line.
			ret += this.print(node.right, level + 1); // recurse right
			ret += this.print(node.left, level + 1); // recurse 
		}
		return ret;
	}
}
