package adt.hashtable.open;

import adt.hashtable.hashfunction.HashFunctionClosedAddressMethod;
import adt.hashtable.hashfunction.HashFunctionQuadraticProbing;

public class HashtableOpenAddressQuadraticProbingImpl<T extends Storable> extends AbstractHashtableOpenAddress<T> {

	public HashtableOpenAddressQuadraticProbingImpl(int size, HashFunctionClosedAddressMethod method, int c1, int c2) {
		super(size);
		hashFunction = new HashFunctionQuadraticProbing<T>(size, method, c1, c2);
		this.initiateInternalTable(size);
	}

	@Override
	public void insert(T element) {
		if (isFull()) {
			throw new HashtableOverflowException();

		} else if (element != null) {
			int hash;

			for (int probe = 0; probe < capacity(); probe++) {
				hash = calculeHash(element, probe);

				if (this.table[hash] == null || this.table[hash].equals(new DELETED())) {
					this.table[hash] = element;
					this.elements++;
					return;
				} else {
					if (this.table[hash].equals(element)) {
						return;
					}
					hash = calculeHash(element, probe);
					this.COLLISIONS++;
				}
			}

		}
	}

	@Override
	public void remove(T element) {
		if (element != null && !isEmpty()) {

			int index = indexOf(element);

			if (index != -1) {
				this.table[index] = new DELETED();
				this.elements--;
			}
		}
	}

	@Override
	public T search(T element) {
		if (element != null && !isEmpty()) {
			int index = indexOf(element);

			if (index != -1) {
				return element;
			}
		}
		return null;
	}

	@Override
	public int indexOf(T element) {
		if (element != null && !isEmpty()) {
			int probe = 0;
			int hash = calculeHash(element, probe);

			while (this.table[hash] != null && probe < capacity()) {
				if (this.table[hash].equals(element)) {
					return hash;
				}
				probe++;
				hash = calculeHash(element, probe);
			}
		}
		return -1;

	}

	private int calculeHash(T element, int probe) {
		return ((HashFunctionQuadraticProbing<T>) hashFunction).hash(element, probe);
	}

}
