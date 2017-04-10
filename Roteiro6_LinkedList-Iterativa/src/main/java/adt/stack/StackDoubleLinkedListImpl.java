package adt.stack;

import adt.linkedList.DoubleLinkedListImpl;

public class StackDoubleLinkedListImpl<T> implements Stack<T> {

	protected DoubleLinkedListImpl<T> top;
	protected int size;

	public StackDoubleLinkedListImpl(int size) {
		this.size = size;
		this.top = new DoubleLinkedListImpl<T>();
	}
	
	@Override
	public T top() {
		return top.getLast().getData();
	}

	@Override
	public boolean isEmpty() {
		return top.isEmpty();
	}

	@Override
	public boolean isFull() {
		return (top.size() == size);
	}

	@Override
	public void push(T element) throws StackOverflowException {
		if (element != null) {
			if (isFull()) {
				throw new StackOverflowException();
				
			} else {
				top.insert(element);
			}
		}

	}

	@Override
	public T pop() throws StackUnderflowException {
		if (isEmpty()) {
			throw new StackUnderflowException();
			
		} else {
			T deletedElement = top.getLast().getData();
			top.removeLast();
			
			return deletedElement;
		}
	}
}
