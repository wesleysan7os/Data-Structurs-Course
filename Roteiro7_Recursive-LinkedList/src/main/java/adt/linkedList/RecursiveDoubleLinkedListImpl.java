package adt.linkedList;

public class RecursiveDoubleLinkedListImpl<T> extends RecursiveSingleLinkedListImpl<T> implements DoubleLinkedList<T> {

	protected RecursiveDoubleLinkedListImpl<T> previous;

	public RecursiveDoubleLinkedListImpl() {

	}

	public RecursiveDoubleLinkedListImpl(T data, RecursiveSingleLinkedListImpl<T> next,
			RecursiveDoubleLinkedListImpl<T> previous) {
		super(data, next);
		this.previous = previous;
	}

	@Override
	public void insert(T element) {
		if (element != null) {
			if (isEmpty()) {
				RecursiveDoubleLinkedListImpl<T> newNext = new RecursiveDoubleLinkedListImpl<>();
				data = element;
				next = newNext;
				newNext.setPrevious(this);
				
			} else {
				next.insert(element);
			}
		}
	}
	
	@Override
	public void remove(T element) {
		if (!isEmpty()) {
			if (data.equals(element)) {
				if (next.getData() == null) {
					data = null;
					
				} else {
					data = next.getData();
					next = next.getNext();
					((RecursiveDoubleLinkedListImpl<T>) next).setPrevious(previous);
				}
				
			} else {
				next.remove(element);
			}
		}
	}

	@Override
	public void insertFirst(T element) {
		if (element != null) {
			if (isFirstNode()) {
				makeNodeAndInsertFirst(element);
				
			} else {
				previous.insertFirst(element);
			}
		}
	}
	
	private boolean isFirstNode() {
		return (previous == null);
	}
	
	private void makeNodeAndInsertFirst(T element) {
		RecursiveDoubleLinkedListImpl<T> newNode = new RecursiveDoubleLinkedListImpl<>();
		newNode.setData(data);
		newNode.setPrevious(this);
		newNode.setNext(next);
		((RecursiveDoubleLinkedListImpl<T>) next).setPrevious(newNode);
		next = newNode;
		data = element;
	}

	@Override
	public void removeFirst() {
		if (isFirstNode()) {
			T element = data;
			this.remove(element);
			
		} else {
			previous.removeFirst();
		}
	}

	@Override
	public void removeLast() {
		if (next.isEmpty()) {
			data = null;
			
		} else {
			((RecursiveDoubleLinkedListImpl<T>) next).removeLast();
		}
	}

	public RecursiveDoubleLinkedListImpl<T> getPrevious() {
		return previous;
	}

	public void setPrevious(RecursiveDoubleLinkedListImpl<T> previous) {
		this.previous = previous;
	}

}