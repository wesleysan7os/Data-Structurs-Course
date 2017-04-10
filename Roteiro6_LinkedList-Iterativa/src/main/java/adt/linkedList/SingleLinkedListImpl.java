package adt.linkedList;

public class SingleLinkedListImpl<T> implements LinkedList<T> {

	protected SingleLinkedListNode<T> head;
	protected int size;

	public SingleLinkedListImpl() {
		head = new SingleLinkedListNode<T>();
		size = 0;
	}

	@Override
	public boolean isEmpty() {
		return head.isNIL();
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public T search(T element) {
		T searchedElement = null;
		
		if (element != null) {
			SingleLinkedListNode<T> aux = head;
			
			while (!aux.isNIL()) {
				if (aux.getData().equals(element)) {
					searchedElement = aux.getData();
					break;
				}
				
				aux = aux.getNext();
			}
		}

		return searchedElement;
	}

	@Override
	public void insert(T element) {
		if (element != null) {
			SingleLinkedListNode<T> aux = head;
			
			while (!aux.isNIL()) {
				aux = aux.getNext();
			}
			
			aux.setData(element);
			aux.setNext(new SingleLinkedListNode<>());
			size++;
		}
	}

	@Override
	public void remove(T element) {
		if (!isEmpty()) {
			if (head.getData().equals(element)) {
				head = head.getNext();
				size--;
				
			} else {
				SingleLinkedListNode<T> aux = head;
				
				while(!aux.isNIL()) {
					if (aux.getData().equals(element)) {
						aux.setData(aux.getNext().getData());
						aux.setNext(aux.getNext().getNext());
						
						size--;
						break;
					}
					
					aux = aux.getNext();
				}
			}
		}
	}

	@Override
	public T[] toArray() {
		T[] arrayOfNodes = (T[]) new Object[this.size()];
		SingleLinkedListNode<T> aux = head;
		
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
