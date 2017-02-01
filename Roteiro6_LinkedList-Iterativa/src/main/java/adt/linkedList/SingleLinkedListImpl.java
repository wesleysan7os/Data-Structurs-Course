package adt.linkedList;

public class SingleLinkedListImpl<T> implements LinkedList<T> {

	protected SingleLinkedListNode<T> head;
	protected SingleLinkedListNode<T> nilNode;
	protected int size;

	public SingleLinkedListImpl() {
		this.head = new SingleLinkedListNode<T>();
		this.nilNode = new SingleLinkedListNode<T>();
		this.head.setNext(nilNode);
		this.size = 0;
	}

	@Override
	public boolean isEmpty() {
		return this.head.isNIL();
	}

	@Override
	public int size() {
		return this.size;
	}

	@Override
	public T search(T element) {
		T searchedElement = null;
		
		if (element != null) {
			SingleLinkedListNode<T> aux = this.head;
			SingleLinkedListNode<T> next = aux.getNext();
			
			while (!next.isNIL() && !aux.getData().equals(element)) {
				aux = aux.getNext();
				next = next.getNext();
			}
			
			if (aux.getData().equals(element)) {
				searchedElement = aux.getData();
			}
		}

		return searchedElement;
	}

	@Override
	public void insert(T element) {
		if (element != null) {
			if (this.isEmpty()) {
				this.head.setData(element);

			} else {
				SingleLinkedListNode<T> aux = this.head;
				SingleLinkedListNode<T> nodeToBeInserted = new SingleLinkedListNode<>(element, nilNode);

				while (!aux.getNext().isNIL()) {
					aux = aux.getNext();
				}

				aux.setNext(nodeToBeInserted);
			}

			size++;
		}
	}

	@Override
	public void remove(T element) {
		if (element != null && !this.isEmpty()) {
			if (this.head.getData().equals(element) && this.size() == 1) {
				this.head.setData(null);
				size--;
				
			} else if (this.head.getData().equals(element)) {
				this.head = this.head.getNext();
				size--;
				
			} else {
				SingleLinkedListNode<T> aux = this.head;
				
				while(!aux.getNext().getData().equals(element) && !aux.getNext().isNIL()) {
					aux = aux.getNext();
				}
				
				if (aux.getNext().getData().equals(element)) {
					aux.setNext(aux.getNext().getNext());
					size--;
				}
			}
		}
	}

	@Override
	public T[] toArray() {
		T[] arrayOfNodes = (T[]) new Object[this.size()];
		SingleLinkedListNode<T> aux = this.head;
		
		for (int i = 0; i < this.size; i++) {
			arrayOfNodes[i] = aux.getData();
			aux = aux.getNext();
		}
		
		return arrayOfNodes;
	}

	public SingleLinkedListNode<T> getHead() {
		return head;
	}

	public void setHead(SingleLinkedListNode<T> head) {
		this.head = head;
	}

}
