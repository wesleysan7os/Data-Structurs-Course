package adt.hashtable.closed;

import util.Util;

import java.lang.reflect.Method;
import java.util.LinkedList;

import adt.hashtable.hashfunction.HashFunction;
import adt.hashtable.hashfunction.HashFunctionClosedAddress;
import adt.hashtable.hashfunction.HashFunctionClosedAddressMethod;
import adt.hashtable.hashfunction.HashFunctionDivisionMethod;
import adt.hashtable.hashfunction.HashFunctionFactory;

public class HashtableClosedAddressImpl<T> extends AbstractHashtableClosedAddress<T> {

	/**
	 * A hash table with closed address works with a hash function with closed
	 * address. Such a function can follow one of these methods: DIVISION or
	 * MULTIPLICATION. In the DIVISION method, it is useful to change the size
	 * of the table to an integer that is prime. This can be achieved by
	 * producing such a prime number that is bigger and close to the desired
	 * size.
	 * 
	 * For doing that, you have auxiliary methods: Util.isPrime and
	 * getPrimeAbove as documented bellow.
	 * 
	 * The length of the internal table must be the immediate prime number
	 * greater than the given size. For example, if size=10 then the length must
	 * be 11. If size=20, the length must be 23. You must implement this idea in
	 * the auxiliary method getPrimeAbove(int size) and use it.
	 * 
	 * @param desiredSize
	 * @param method
	 */

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public HashtableClosedAddressImpl(int desiredSize, HashFunctionClosedAddressMethod method) {
		int realSize = desiredSize;

		if (method == HashFunctionClosedAddressMethod.DIVISION) {
			realSize = this.getPrimeAbove(desiredSize); // real size must the
														// the immediate prime
														// above
		}
		initiateInternalTable(realSize);
		HashFunction function = HashFunctionFactory.createHashFunction(method, realSize);
		this.hashFunction = function;
	}

	// AUXILIARY
	/**
	 * It returns the prime number that is closest (and greater) to the given
	 * number. You can use the method Util.isPrime to check if a number is
	 * prime.
	 */
	int getPrimeAbove(int number) {
		return Util.isPrime(number) ? number : getPrimeAbove(number + 1);
	}

	@Override
	public void insert(T element) {
		if (element != null) {
			int position = ((HashFunctionClosedAddress<T>) hashFunction).hash(element);

			if (table[position] == null) {
				table[position] = new LinkedList<T>();
				((LinkedList<T>) table[position]).add(element);
				elements++;

			} else {
				((LinkedList<T>) table[position]).add(element);
				elements++;
				
				if (((LinkedList<T>) table[position]).size() > 1) {
					COLLISIONS++;
				}
			}
		}
	}

	@Override
	public void remove(T element) {
		if (element != null) {
			int position = ((HashFunctionClosedAddress<T>) hashFunction).hash(element);
			boolean elementWasRemoved = ((LinkedList<T>) table[position]).remove(element);
			
			if (elementWasRemoved) {
				elements--;
				
				if (((LinkedList<T>) table[position]).size() > 0) {
					COLLISIONS--;
				}
			}
		}
	}

	@Override
	public T search(T element) {
		T elementToBeSearched = null;
		
		if (element != null) {
			int position = ((HashFunctionClosedAddress<T>) hashFunction).hash(element);
			
			if (table[position] != null) {
				boolean containsSearchedElement = ((LinkedList<T>) table[position]).contains(element);
				
				if (containsSearchedElement) {
					elementToBeSearched = element;
				}
			}
		}
		
		return elementToBeSearched;
	}

	@Override
	public int indexOf(T element) {
		int elementIndex = -1;
		
		if (search(element) != null) {
			elementIndex = ((HashFunctionClosedAddress<T>) hashFunction).hash(element);
		}
		
		return elementIndex;
	}

}
