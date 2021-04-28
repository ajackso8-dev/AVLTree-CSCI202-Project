/**
 * 
 */
package edu.unca.csci202;

import java.util.Random;

/**
 * @author Brian Drawert
 * @data Nov 6th, 2020
 * 
 */
public class Main {


	public static void main(String[] args) {		
		
		// Test the LinkedBST implement 
		BinarySearchTreeADT<Integer> bst = new AVLTree<Integer>();
		int[] numbers = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
		for(int i=0;i<numbers.length;i++) {
			System.out.println("_____________________________________________________________");
			System.out.println("inserting "+ numbers[i]+" and printing tree:");
			bst.insert(numbers[i]);
			System.out.println(bst);
		}
		
		
		
		/*int[] numbers2 = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
		for(int i=0;i<numbers2.length;i++) {
			System.out.println("_____________________________________________________________");
			System.out.println("find "+ numbers2[i]+":" + bst.find(numbers2[i]));
			System.out.println("_____________________________________________________________");
			System.out.println("deleting "+ numbers2[i]+" and printing tree:");
			bst.delete(numbers2[i]);
			System.out.println(bst);
			System.out.println("_____________________________________________________________");
			System.out.println("contains "+ numbers2[i]+":" + bst.contains(numbers2[i]));
		}*/
		Random rnd = new Random();
		while(!bst.isEmpty()) {
			int min = bst.minimum();
			int max = bst.maximum();
			int x = rnd.nextInt(max-min+1) + min;
			System.out.println("_____________________________________________________________");
			System.out.println("min="+min+" max="+max+" find "+ x+":" + bst.find(x));
			System.out.println("deleting "+ x+" and printing tree:");
			bst.delete(x);
			System.out.println(bst);
			System.out.println("contains "+ x+":" + bst.contains(x));
		}
			
		
	
		System.exit(0);
		
	
	}
}