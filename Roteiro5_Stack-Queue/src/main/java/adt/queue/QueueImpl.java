package adt.queue;

public class QueueImpl<T> implements Queue<T> {

	private T[] array;
	private int tail;
	private int arraySize;
	private final int OLDEST_ELEMENT = 0;

	@SuppressWarnings("unchecked")
	public QueueImpl(int size) {
		array = (T[]) new Object[size];
		tail = -1;
		arraySize = array.length - 1;
	}

	@Override
	public T head() {
		if (isEmpty()) {
			return null;

		} else {
			return array[OLDEST_ELEMENT];
		}
	}

	@Override
	public boolean isEmpty() {
		return (tail == -1);
	}

	@Override
	public boolean isFull() {
		return (tail == arraySize);
	}

	private void shiftLeft() {
		for (int i = 0; i < tail; i++) {
			array[i] = array[i + 1];
		}
	}

	@Override
	public void enqueue(T element) throws QueueOverflowException {
		if (element != null) {
			if (isFull()) {
				throw new QueueOverflowException();

			} else {
				array[++tail] = element;
			}
		}
	}

	@Override
	public T dequeue() throws QueueUnderflowException {
		if (isEmpty()) {
			throw new QueueUnderflowException();

		} else {
			T deletedElement = array[OLDEST_ELEMENT];
			shiftLeft();
			tail--;

			return deletedElement;
		}
	}

}
