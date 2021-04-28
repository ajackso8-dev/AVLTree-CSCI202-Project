/**
 * 
 */
package edu.unca.csci202;


/**
 * @author Brian Drawert
 * @data Oct 28th, 2020
 */
public interface BinarySearchTreeADT<T extends Comparable<T>> extends BinaryTreeADT<T> {

	/**
	 * Insert into the BST.
	 * @param element to insert
	 * @return number edges traversed to insert this element
	 */
	public int insert(T element);   // Lab 10
	
	/**
	 * Get height of tree in number of nodes
	 * @returns height of tree in number of nodes
	 */	
	public int height();            // Lab 10

	/**
	 * Return the largest element in the tree
	 * @return the largest element in the tree
	 */
	public T maximum();             // Lab 10

	/**
	 * Return the smallest element in the tree
	 * @return the smallest element in the tree
	 */
	public T minimum();             // Lab 10

	/**
	 * Remove an element from the tree
	 * @param element to be removed from the tree
	 */
	public void delete(T element);	// project 6

	/**
	 * Find and return an element in the tree that is equal to parameter
	 * @param element to be searched for
	 * @return element found, or null if not found in tree
	 */
	public T find(T element);       // project 6 - override BinaryTree
	
	/**
	 * Determine if an element in the tree that is equal to the parameter exists
	 * @param element to be searched for
	 * @return true if matching element exists, false otherwise
	 */
	boolean contains(T element);    // project 6 - override BinaryTree
	
	/**
	 * Return the number of elements in the tree
	 * @return the number of elements in the tree
	 */
	public int size();              // project 6 - override BinaryTree
	
	

	
}
