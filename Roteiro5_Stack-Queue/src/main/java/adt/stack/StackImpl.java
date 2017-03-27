package adt.stack;

public class StackImpl<T> implements Stack<T> {

	private T[] array;
	private int top;
	private int arraySize;

	@SuppressWarnings("unchecked")
	public StackImpl(int size) {
		array = (T[]) new Object[size];
		top = -1;
		arraySize = array.length - 1;
	}

	@Override
	public T top() {
		if (isEmpty()) {
			return null;

		} else {
			return array[top];
		}
	}

	@Override
	public boolean isEmpty() {
		return (top == -1);
	}

	@Override
	public boolean isFull() {
		return (top == arraySize);
	}

	@Override
	public void push(T element) throws StackOverflowException {
		if (element != null) {
			if (isFull()) {
				throw new StackOverflowException();

			} else {
				array[++top] = element;
			}
		}
	}

	@Override
	public T pop() throws StackUnderflowException {
		if (isEmpty()) {
			throw new StackUnderflowException();

		} else {
			T deletedElement = array[top];
			top--;

			return deletedElement;
		}
	}

}
