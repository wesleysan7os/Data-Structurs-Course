package adt.hashtable.open;

import adt.hashtable.hashfunction.HashFunctionClosedAddressMethod;
import adt.hashtable.hashfunction.HashFunctionLinearProbing;

public class HashtableOpenAddressLinearProbingImpl<T extends Storable> extends AbstractHashtableOpenAddress<T> {

	public HashtableOpenAddressLinearProbingImpl(int size, HashFunctionClosedAddressMethod method) {
		super(size);
		hashFunction = new HashFunctionLinearProbing<T>(size, method);
		initiateInternalTable(size);
	}

	@Override
	public void insert(T element) {
		if (isFull())
			throw new HashtableOverflowException();
		
		if (element == null || search(element) != null)
			return;
		
		int probe = 0;
		int position = ((HashFunctionLinearProbing<T>) hashFunction).hash(element, probe);
		
		while  ( table[position] != null
			 && !table[position].equals(new DELETED()) ) {
			
			COLLISIONS++;
			probe++;
			position = ((HashFunctionLinearProbing<T>) hashFunction).hash(element, probe);
		}
		
		table[position] = element;
		elements++;
	
	}

	@Override
	public void remove(T element) {
		T value = search(element);
		
		if (value == null || isEmpty())
			return;
		
		table[indexOf(element)] = new DELETED();
		elements--;
	}

	@Override
	public T search(T element) {
		
		T result = null;
		
		if (element == null || isEmpty())
			return result;
		
		if (indexOf(element) != -1)
			result = element;
		
		return result;
	}

	@Override
	public int indexOf(T element) {
		if (element == null)
			return -1;
		
		int probe = 0;
		int position = ((HashFunctionLinearProbing<T>) hashFunction).hash(element, probe);
		
		while (table[position] != null && probe < capacity()) {
		
			if (table[position].equals(element))
				return position;
			
			probe++;
			position = ((HashFunctionLinearProbing<T>) hashFunction).hash(element, probe);
		}
		
		return -1;
	}
	
}
