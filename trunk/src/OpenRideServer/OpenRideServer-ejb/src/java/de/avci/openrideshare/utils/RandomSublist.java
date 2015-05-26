package de.avci.openrideshare.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * A random sublist. That is: Given a list "l" of n elements of class T and a number
 * m<=n, the random sublist provides a list of m T-elements picked at random from l.
 * The order in of the element in the random is also not determined by the order 
 * of elements in the source.
 * 
 * The random sublist was designed to enforce the matchLimit feature
 * 
 * 
 * 
 * @author jochen
 *
 */
public class RandomSublist<T> extends LinkedList<T> {

	/** Dumb default.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create a new RandomList with size "bagSize" from elements in list.
	 * 
	 * @param list
	 */

	public RandomSublist(List<T> list, int bagSize) {
		
		super();
		this.initializeInternalList(list, bagSize);
	}

	/**
	 * Create a new RandomList with size "bagSize" equal to sourceList
	 * 
	 * @param list
	 */

	public RandomSublist(List<T> list) {

		super();
		this.initializeInternalList(list, list.size());
	}

	
	
	
	
	
	
	
	/**
	 * Initialize the internalList with "bagSize" elements of the argument List
	 * chosen at random.
	 * 
	 * @param list
	 *            list from which to pick elements from
	 * @param bagSize
	 *            number of elements to be added to the internal list
	 */
	private void initializeInternalList(List<T> srcList, int bagSize) {

		if (bagSize <= 0) { // empty internal list
			return;
		}


		// Create an array list allowng random Access to elments by index
		// copy elements to an ArrayList to allow random access to ListElements
		ArrayList<T> arList = new ArrayList<T>(srcList.size());
		arList.addAll(srcList);

		// pick backSize elements at random...
		for (int i = 0; i < Math.min(srcList.size(),bagSize); i++) {
			Random random = new Random();
			int randomIndex = random.nextInt(arList.size());
			this.add(arList.get(randomIndex));
			arList.remove(randomIndex);
		}
	}
}
