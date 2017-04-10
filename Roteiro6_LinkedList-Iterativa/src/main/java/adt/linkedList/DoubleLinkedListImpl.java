package adt.linkedList;

public class DoubleLinkedListImpl<T> extends SingleLinkedListImpl<T> implements DoubleLinkedList<T> {

	protected DoubleLinkedListNode<T> last;
	protected DoubleLinkedListNode<T> nilNode; // para evitar cast

	public DoubleLinkedListImpl() {
		super();
		nilNode = new DoubleLinkedListNode<>();
		last = new DoubleLinkedListNode<>();
		last.setNext(nilNode);
		head = last;
	}

	@Override
	public void insert(T element) {
		if (element != null) {
			if (isEmpty()) {
				insertFirst(element);

			} else {
				DoubleLinkedListNode<T> nodeToBeInserted = new DoubleLinkedListNode<T>(element, nilNode, last);
				last.setNext(nodeToBeInserted);
				last = nodeToBeInserted;
				size++;
			}
		}
	}

	@Override
	public void insertFirst(T element) {
		if (element != null) {
			if (isEmpty()) {
				head = last;
				head.setData(element);

			} else {
				DoubleLinkedListNode<T> nodeToBeInserted = new DoubleLinkedListNode<T>(element,
						(DoubleLinkedListNode<T>) head, nilNode);
				((DoubleLinkedListNode<T>) head).setPrevious(nodeToBeInserted);

				head = nodeToBeInserted;
			}

			size++;
		}
	}

	@Override
	public void remove(T element) {
		if (element != null && !isEmpty()) {
			if (head.getData().equals(element)) {
				removeFirst();

			} else if (last.getData().equals(element)) {
				removeLast();

			} else if (size() > 1) {
				SingleLinkedListNode<T> aux = head.getNext();

				while (!aux.isNIL() && !aux.getData().equals(element)) {
					aux = aux.getNext();
				}

				if (!aux.isNIL()) {
					((DoubleLinkedListNode<T>) aux).getPrevious().setNext(aux.getNext());
					((DoubleLinkedListNode<T>) aux.getNext())
							.setPrevious(((DoubleLinkedListNode<T>) aux).getPrevious());

					size--;
				}
			}
		}
	}

	@Override
	public void removeFirst() {
		if (!isEmpty()) {
			if (hasJustOneNode()) {
				head.setData(null);
				
			} else {
				head = head.getNext();
				((DoubleLinkedListNode<T>) head).setPrevious(nilNode);
			}
			
			size--;
		}
	}

	@Override
	public void removeLast() {
		if (!isEmpty()) {
			if (hasJustOneNode()) {
				head.setData(null);
				
			} else {
				last = last.getPrevious();
				last.setNext(nilNode);
				size--;
			}
		}
	}
	
	private boolean hasJustOneNode() {
		return (size == 1);
	}

	public DoubleLinkedListNode<T> getLast() {
		return last;
	}

	public void setLast(DoubleLinkedListNode<T> last) {
		this.last = last;
	}

}
